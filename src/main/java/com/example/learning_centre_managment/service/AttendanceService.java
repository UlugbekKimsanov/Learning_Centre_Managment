package com.example.learning_centre_managment.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.learning_centre_managment.dto.request.AttendanceCr;
import uz.pdp.learning_centre_managment.dto.response.AttendanceResponse;
import uz.pdp.learning_centre_managment.dto.response.LessonAttendanceResponse;
import uz.pdp.learning_centre_managment.dto.response.LessonResponse;
import uz.pdp.learning_centre_managment.entity.AttendanceEntity;
import uz.pdp.learning_centre_managment.entity.LessonEntity;
import uz.pdp.learning_centre_managment.entity.StudentInfo;
import uz.pdp.learning_centre_managment.entity.enums.LessonStatus;
import uz.pdp.learning_centre_managment.exception.DataNotFoundException;
import uz.pdp.learning_centre_managment.exception.DuplicateValueException;
import uz.pdp.learning_centre_managment.repository.AttendanceRepository;
import uz.pdp.learning_centre_managment.repository.GroupRepository;
import uz.pdp.learning_centre_managment.repository.LessonRepository;
import uz.pdp.learning_centre_managment.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final ModelMapper modelMapper;
    private final AttendanceRepository attendanceRepository;
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    public AttendanceResponse create(AttendanceCr attendanceDto) {
        checkLesson(attendanceDto.getLessonId());
        checkStudent(attendanceDto.getStudentId());
        AttendanceEntity attendance = attendanceRepository.save(modelMapper.map(attendanceDto,AttendanceEntity.class));
        return modelMapper.map(attendance,AttendanceResponse.class);
    }
    public List<LessonAttendanceResponse> getStudentAttendances(UUID id) {

        List<AttendanceEntity> attendanceEntityList = attendanceRepository.findAllByStudentId( studentRepository.findStudentInfoByUserEntityId(id).get().getId());
        return modelMapper.map(attendanceEntityList, new TypeToken<List<AttendanceResponse>>() {}.getType());

    }
    public ResponseEntity<String> createAttendances(List<AttendanceCr> attendanceDtoList) {
        List<StudentInfo> all = studentRepository.findAll();
        for (AttendanceCr attendanceDto : attendanceDtoList) {
            checkLesson(attendanceDto.getLessonId());
            checkStudent(attendanceDto.getStudentId());
            checkAttendance(attendanceDto);
        }
        for (AttendanceCr attendanceDto : attendanceDtoList) {
            StudentInfo byId = studentRepository.findById(attendanceDto.getStudentId()).get();
            byId.setRating(byId.getRating()+attendanceDto.getPoints());
            studentRepository.save(byId);
            attendanceRepository.save(modelMapper.map(attendanceDto,AttendanceEntity.class));
        }
        return ResponseEntity.ok("Saved");
    }

    private void checkAttendance(AttendanceCr attendanceDto) {
        List<AttendanceEntity> allStudentAttendance = attendanceRepository.findAllByStudentId(attendanceDto.getStudentId());
        for (AttendanceEntity attendance : allStudentAttendance) {
            if(Objects.equals(attendance.getLessonId(),attendanceDto.getLessonId()) &&
                Objects.equals(attendance.getStudentId(),attendanceDto.getStudentId())){
                throw new DuplicateValueException("Duplicate data!");
            }
        }
    }

    private LessonEntity checkLesson(UUID lessonId){
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new DataNotFoundException("Lesson not found by this Id " + lessonId));

    }

    private StudentInfo checkStudent(UUID studentId){
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new DataNotFoundException("Student not found by this Id " +  studentId));
    }

    public List<AttendanceResponse> getAttendancesByLessonId(UUID lessonId){
        List<AttendanceEntity> attendanceEntityList = attendanceRepository.findAllByLessonId(lessonId);
        return modelMapper.map(attendanceEntityList, new TypeToken<List<AttendanceResponse>>() {}.getType());

    }
    public List<LessonAttendanceResponse> getAttendanceWithLessonByModule(UUID groupId,Integer module){
        List<LessonEntity> lessonEntityList = lessonRepository.findLessonEntitiesByGroupId(groupId);
        List<LessonEntity> lessonEntitiesByModule = lessonRepository.findLessonEntitiesByModule(module);
        List<LessonAttendanceResponse> lessonAttendanceResponseList = new ArrayList<>();

        for (LessonEntity lesson : lessonEntitiesByModule) {
            if(lesson.getLessonStatus() == LessonStatus.FINISHED){
                LessonAttendanceResponse lessonAttendanceResponse = new LessonAttendanceResponse();
                lessonAttendanceResponse.setLessonResponse(modelMapper.map(lesson, LessonResponse.class));
                lessonAttendanceResponse.setAttendanceResponseList(getAttendancesByLessonId(lesson.getId()));
                lessonAttendanceResponseList.add(lessonAttendanceResponse);
            }

        }

        return lessonAttendanceResponseList;
    }
    public List<LessonAttendanceResponse> getAllAttendancesWithLesson(UUID groupId,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LessonEntity> allByGroupId = lessonRepository.findAllByGroupId(pageable, groupId);
        List<LessonAttendanceResponse> lessonAttendanceResponseList = new ArrayList<>();
        for (LessonEntity lesson : allByGroupId) {
            if (lesson.getModule()==groupRepository.findById(groupId).get().getModule()){
            LessonAttendanceResponse lessonAttendanceResponse = new LessonAttendanceResponse();
            lessonAttendanceResponse.setLessonResponse(modelMapper.map(lesson, LessonResponse.class));
            lessonAttendanceResponse.setAttendanceResponseList(getAttendancesByLessonId(lesson.getId()));
            lessonAttendanceResponseList.add(lessonAttendanceResponse);}
        }
        return lessonAttendanceResponseList;
    }

}
