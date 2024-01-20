package com.example.learning_centre_managment.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.learning_centre_managment.config.jwt.JwtService;
import uz.pdp.learning_centre_managment.dto.request.AuthDto;
import uz.pdp.learning_centre_managment.dto.request.UserCr;
import uz.pdp.learning_centre_managment.dto.request.UserUpdate;
import uz.pdp.learning_centre_managment.dto.response.JwtResponse;
import uz.pdp.learning_centre_managment.dto.response.MentorResponse;
import uz.pdp.learning_centre_managment.dto.response.StudentResponse;
import uz.pdp.learning_centre_managment.dto.response.UserResponse;
import uz.pdp.learning_centre_managment.entity.GroupEntity;
import uz.pdp.learning_centre_managment.entity.MentorInfo;
import uz.pdp.learning_centre_managment.entity.StudentInfo;
import uz.pdp.learning_centre_managment.entity.UserEntity;
import uz.pdp.learning_centre_managment.entity.enums.UserRole;
import uz.pdp.learning_centre_managment.exception.BadRequestException;
import uz.pdp.learning_centre_managment.exception.DataNotFoundException;
import uz.pdp.learning_centre_managment.repository.*;

import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

import static uz.pdp.learning_centre_managment.entity.enums.UserRole.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository ;
    private final ModelMapper modelMapper;
    private final MailSenderService senderService;
    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;
    private final GroupRepository groupRepository;
    private final AttendanceRepository attendanceRepository;
    private final JwtService jwtService;
    public UserResponse addAdmin(UserCr userCr){
        String verificationCode = senderService.sendVerificationCode(userCr.getEmail());
        UserEntity save = userRepository.save(modelMapper.map(userCr, UserEntity.class));
        save.setEmail(userCr.getEmail());
        save.setPassword(verificationCode);
        save.setRole(UserRole.ADMIN);
        userRepository.save(save);
        return modelMapper.map( save,UserResponse.class);
    }

    public JwtResponse signIn(AuthDto dto) {
        UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new DataNotFoundException("user not found"));
        if (dto.getPassword().equals(user.getPassword())) {
            return new JwtResponse(jwtService.generateToken(user));
        }
        throw new AuthenticationCredentialsNotFoundException("password didn't match");
    }
    public  <T> T me(Principal principal) {
        UserEntity userEntity = userRepository.findById(UUID.fromString(principal.getName()))
                .orElseThrow(() -> new DataNotFoundException("User not found!"));;
        UserRole role = userEntity.getRole();
        if (role == STUDENT){
            StudentResponse studentResponse = modelMapper.map(userEntity,StudentResponse.class);
            StudentInfo studentInfo = studentRepository.findStudentInfoByUserEntityId(userEntity.getId())
                    .orElseThrow(() -> new DataNotFoundException("Not found!"));
            studentResponse.setId(studentInfo.getId());
            studentResponse.setGroupId(studentInfo.getGroupId());
            studentResponse.setRating(studentInfo.getRating());
            return (T) studentResponse;
        } else if (role == MENTOR) {
            MentorResponse mentorResponse =  modelMapper.map(userEntity,MentorResponse.class);
            MentorInfo mentorInfo = mentorRepository.findMentorInfoByUserEntityId(userEntity.getId())
                .orElseThrow(() -> new DataNotFoundException("Not found!"));
            mentorResponse.setSalary(mentorInfo.getSalary());
            mentorResponse.setExperience(mentorInfo.getExperience());
            mentorResponse.setSubject(mentorInfo.getSubject());
            return (T)mentorResponse;
        }
        return (T) modelMapper.map(userEntity,UserResponse.class);
    }


    public String delete(Principal principal) {
        UserEntity userEntity = userRepository.findById(UUID.fromString(principal.getName()))
                .orElseThrow(() -> new DataNotFoundException("User not found!"));;
        UserRole role = userEntity.getRole();
        if (role == STUDENT){
            StudentInfo studentInfo = studentRepository.findStudentInfoByUserEntityId(userEntity.getId())
                    .orElseThrow(() -> new DataNotFoundException("Student not found!"));
            studentRepository.delete(studentInfo);
            userRepository.deleteById(userEntity.getId());
            attendanceRepository.deleteByStudentId(studentInfo.getId());
            GroupEntity groupEntity = groupRepository.findById(studentInfo.getGroupId()).get();
            groupEntity.setStudentCount(groupEntity.getStudentCount() - 1);
            groupRepository.save(groupEntity);
            return "Deleted";
        }
        else if (role == MENTOR){
            MentorInfo mentorInfo = mentorRepository.findMentorInfoByUserEntityId(userEntity.getId())
                    .orElseThrow(() -> new DataNotFoundException("Mentor not found!"));
            mentorRepository.delete(mentorInfo);
            userRepository.deleteById(userEntity.getId());
            return "Deleted";
        }
        else {
            UserEntity userEntity1 = userRepository.findById(userEntity.getId()).
                    orElseThrow(() -> new DataNotFoundException("Admin not found!"));
            if (userEntity1.getRole() != SUPER_ADMIN){
                userRepository.deleteById(userEntity1.getId());
                return "Deleted";
            } else {
                throw new BadRequestException("Super Admin cannot deleted");
            }
        }
    }

    public UserResponse update(UserUpdate userUpdate, Principal principal) {
        UserEntity userEntity1 = userRepository.findById(UUID.fromString(principal.getName())).
                orElseThrow(() -> new DataNotFoundException("Admin not found!"));
        if(!Objects.equals(userUpdate.getName(),null)){
            userEntity1.setName(userUpdate.getName());
        }if(!Objects.equals(userUpdate.getSurname(),null)){
            userEntity1.setSurname(userUpdate.getSurname());
        }if(!Objects.equals(userUpdate.getPhoneNumber(),null)){
            userEntity1.setPhoneNumber(userUpdate.getPhoneNumber());
        }if(!Objects.equals(userUpdate.getEmail(),null)){
            userEntity1.setEmail(userUpdate.getEmail());
        }if(!Objects.equals(userUpdate.getPassword(),null)){
            userEntity1.setPassword(userUpdate.getPassword());
        }
        UserEntity entity = userRepository.save(userEntity1);
        return modelMapper.map(entity,UserResponse.class);
    }
}
