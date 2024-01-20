package com.example.learning_centre_managment.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.learning_centre_managment.dto.request.GroupCr;
import uz.pdp.learning_centre_managment.dto.request.UpdateGroupDto;
import uz.pdp.learning_centre_managment.dto.response.GroupResponse;
import uz.pdp.learning_centre_managment.entity.GroupEntity;
import uz.pdp.learning_centre_managment.entity.LessonEntity;
import uz.pdp.learning_centre_managment.entity.enums.GroupStatus;
import uz.pdp.learning_centre_managment.entity.enums.LessonStatus;
import uz.pdp.learning_centre_managment.exception.DataNotFoundException;
import uz.pdp.learning_centre_managment.exception.DuplicateValueException;
import uz.pdp.learning_centre_managment.repository.CourseRepository;
import uz.pdp.learning_centre_managment.repository.GroupRepository;
import uz.pdp.learning_centre_managment.repository.LessonRepository;
import uz.pdp.learning_centre_managment.repository.MentorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final MentorRepository mentorRepository;
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

     public GroupResponse create(GroupCr groupCr) {
        if (groupRepository.existsByGroupName(groupCr.getGroupName())) {
            throw new DuplicateValueException("This group already exists with name '" + groupCr.getGroupName() + "'");
        }
        if(courseRepository.findById(groupCr.getCourseId()).isEmpty()) {
            throw new DataNotFoundException("Course not found by this id " + groupCr.getCourseId());
        }
        if(mentorRepository.findById(groupCr.getMentorId()).isEmpty()) {
            throw new DataNotFoundException("this mentor not found");
        }
         courseRepository.findById(groupCr.getCourseId()).get();
        GroupResponse group = new GroupResponse();
        group.setGroupName(groupCr.getGroupName());
        group.setStudentCount(0);
        group.setCourseId(groupCr.getCourseId());
        group.setMentorId(groupCr.getMentorId());
        group.setModule(1);
        group.setStatus(GroupStatus.CREATED);
         GroupEntity groupEntity = groupRepository.save(modelMapper.map(group, GroupEntity.class));
         group.setId(groupEntity.getId());
         for (int i = 1; i <=12 ; i++) {
             LessonEntity lessonEntity = new  LessonEntity();
             lessonEntity.setGroupId(groupEntity.getId());
             lessonEntity.setLessonNumber(i);
             lessonEntity.setModule(1);
             lessonEntity.setLessonStatus(LessonStatus.CREATED);
             lessonRepository.save(lessonEntity);
         }
        return group;
    }
    public GroupResponse findById(UUID id) {
        return modelMapper.map(groupRepository.findById(id), GroupResponse.class);
    }

    public GroupResponse update(UUID groupId, UpdateGroupDto updateGroupDto) {
        GroupEntity group = groupRepository.findById(groupId).orElseThrow(
                () -> new DataNotFoundException("group not found"));
        group.setMentorId(updateGroupDto.getMentorId());
        group.setGroupName(updateGroupDto.getGroupName());
        groupRepository.save(group);
         return modelMapper.map(group, GroupResponse.class);
    }

    public List<GroupEntity> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return groupRepository.findAll(pageable).getContent();
    }


    public List<GroupResponse> getByMentorId(UUID mentorId) {
         List<GroupEntity> groupEntityList = groupRepository.findAllByMentorId(mentorId);
        return modelMapper.map(groupEntityList, new TypeToken<List<GroupResponse>>() {}.getType());
    }

    public List<GroupResponse> getByCourseId(UUID courseId) {
        List<GroupEntity> groupEntityList = groupRepository.findAllByCourseId(courseId);
        return modelMapper.map(groupEntityList, new TypeToken<List<GroupResponse>>() {}.getType());
    }

    public List<GroupResponse> getAvailableGroupsByCourseId(UUID courseId) {
         List<GroupEntity> availableGroups = new ArrayList<>();
        List<GroupEntity> groupEntityList = groupRepository.findAllByCourseId(courseId);
        for (GroupEntity groupEntity : groupEntityList) {
            if(groupEntity.getStudentCount()<20){
                availableGroups.add(groupEntity);
            }
        }
        return modelMapper.map(availableGroups, new TypeToken<List<GroupResponse>>() {}.getType());
    }
}
