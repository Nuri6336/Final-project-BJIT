package com.healthcare.userservice.service.implementation;

import com.healthcare.userservice.constants.AppConstants;
import com.healthcare.userservice.dto.DoctorAllocationDto;
import com.healthcare.userservice.dto.DoctorDto;
import com.healthcare.userservice.dto.DoctorProfileDto;
import com.healthcare.userservice.entity.DoctorEntity;
import com.healthcare.userservice.entity.UserEntity;
import com.healthcare.userservice.repository.DoctorRepository;
import com.healthcare.userservice.repository.UserRepository;
import com.healthcare.userservice.service.DoctorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DoctorServiceImplementation implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String updateDoctorInfo(DoctorDto doctorDto) throws Exception {
       DoctorEntity doctorEntity = doctorRepository
                .findByDoctorUniqueID(getCurrentUser().getUniqueId())
                .orElseThrow(() -> new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

        doctorEntity.setAge(doctorDto.getAge());
        doctorEntity.setProfessionalDescription(doctorDto.getProfessionalDescription());
        doctorEntity.setQualifications(doctorDto.getQualifications());
        doctorEntity.setSpecialities(doctorDto.getSpecialities());
        doctorEntity.setAddress(doctorDto.getAddress());

        return "Updated successfully";
    }


    @Override
    public DoctorProfileDto viewProfile() throws Exception {
        String doctorId = getCurrentUser().getUniqueId();

        DoctorEntity doctorEntity = doctorRepository.findByDoctorUniqueID(doctorId)
                .orElseThrow(()->new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

        UserEntity userEntity = userRepository.findByUniqueId(doctorId)
                .orElseThrow(()->new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

        DoctorProfileDto returnDto = getDoctorProfileDto(doctorEntity, userEntity);

        return returnDto;
    }

    @Override
    public DoctorProfileDto viewProfileByUniqueId(String doctorId) throws Exception {

        DoctorEntity doctorEntity = doctorRepository.findByDoctorUniqueID(doctorId)
                .orElseThrow(()->new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

        UserEntity userEntity = userRepository.findByUniqueId(doctorId)
                .orElseThrow(()->new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

        DoctorProfileDto returnDto = getDoctorProfileDto(doctorEntity, userEntity);

        return returnDto;
    }

    private static DoctorProfileDto getDoctorProfileDto(DoctorEntity doctorEntity, UserEntity userEntity) {
        DoctorProfileDto returnDto = new DoctorProfileDto();

        returnDto.setAddress(doctorEntity.getAddress());
        returnDto.setAge(doctorEntity.getAge());
        returnDto.setProfessionalDescription(doctorEntity.getProfessionalDescription());
        returnDto.setQualifications(doctorEntity.getQualifications());
        returnDto.setSpecialities(doctorEntity.getSpecialities());
        returnDto.setRoomNo(doctorEntity.getRoomNo());

        returnDto.setUniqueId(userEntity.getUniqueId());
        returnDto.setGender(userEntity.getGender());
        returnDto.setEmail(userEntity.getEmail());
        returnDto.setFname(userEntity.getFname());
        returnDto.setLname(userEntity.getLname());
        returnDto.setMobile(userEntity.getMobile());

        return returnDto;
    }

    private UserEntity getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new Exception(AppConstants.TOKEN_INVALID));
    }

    @Override
    public String addOrUpdateRoomAllocation(String doctorId, DoctorAllocationDto doctorAllocationDto) {
        DoctorEntity doctorEntity = doctorRepository.findByDoctorUniqueID(doctorId)
                .orElseThrow(() -> new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

        // Update only the specified fields
        if (doctorAllocationDto.getRoomNo() != null) {
            doctorEntity.setRoomNo(doctorAllocationDto.getRoomNo());
        }

        if (doctorAllocationDto.getDutySlot() != null) {
            doctorEntity.setDutySlot(doctorAllocationDto.getDutySlot());
        }

        // Save the updated entity
        doctorRepository.save(doctorEntity);
        return "Successfully stored";
    }

    //For search doctor by doctor name
    @Override
    public List<DoctorProfileDto> findDoctorsByName(String doctorName) throws Exception {
        List<UserEntity> userEntities = userRepository.findByFname(doctorName);

        if (userEntities.isEmpty()) {
            throw new UsernameNotFoundException("No users found with the given name");
        }

        return userEntities.stream()
                .filter(userEntity -> userEntity.getUniqueId().startsWith("DOC"))
                .flatMap(userEntity -> getDoctorProfileDtos(userEntity).stream())
                .collect(Collectors.toList());
    }

    private List<DoctorProfileDto> getDoctorProfileDtos(UserEntity userEntity) {
        DoctorEntity doctorEntity = doctorRepository.findByDoctorUniqueID(userEntity.getUniqueId())
                .orElseThrow(() -> new UsernameNotFoundException("No doctor found for the user"));

        DoctorProfileDto returnDto = new DoctorProfileDto();
        returnDto.setAddress(doctorEntity.getAddress());
        returnDto.setAge(doctorEntity.getAge());
        returnDto.setProfessionalDescription(doctorEntity.getProfessionalDescription());
        returnDto.setQualifications(doctorEntity.getQualifications());
        returnDto.setSpecialities(doctorEntity.getSpecialities());
        returnDto.setRoomNo(doctorEntity.getRoomNo());

        returnDto.setUniqueId(userEntity.getUniqueId());
        returnDto.setGender(userEntity.getGender());
        returnDto.setEmail(userEntity.getEmail());
        returnDto.setFname(userEntity.getFname());
        returnDto.setLname(userEntity.getLname());
        returnDto.setMobile(userEntity.getMobile());

        return List.of(returnDto);
    }

    // Service method for finding doctors by specialty
    @Override
    public List<DoctorProfileDto> findDoctorsBySpecialty(String specialty) {
        List<DoctorEntity> doctorEntities = doctorRepository.findBySpecialities(specialty);

        if (doctorEntities.isEmpty()) {
            throw new UsernameNotFoundException("No doctors found with the given specialty");
        }

        return doctorEntities.stream()
                .map(this::getDoctorProfileDtoByDoctorEntity)
                .collect(Collectors.toList());
    }

    private DoctorProfileDto getDoctorProfileDtoByDoctorEntity(DoctorEntity doctorEntity) {
        DoctorProfileDto returnDto = new DoctorProfileDto();

        returnDto.setAddress(doctorEntity.getAddress());
        returnDto.setAge(doctorEntity.getAge());
        returnDto.setProfessionalDescription(doctorEntity.getProfessionalDescription());
        returnDto.setQualifications(doctorEntity.getQualifications());
        returnDto.setSpecialities(doctorEntity.getSpecialities());
        returnDto.setRoomNo(doctorEntity.getRoomNo());

        UserEntity userEntity = userRepository.findByUniqueId(doctorEntity.getDoctorUniqueID())
                .orElseThrow(() -> new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

        returnDto.setUniqueId(userEntity.getUniqueId());
        returnDto.setGender(userEntity.getGender());
        returnDto.setEmail(userEntity.getEmail());
        returnDto.setFname(userEntity.getFname());
        returnDto.setLname(userEntity.getLname());
        returnDto.setMobile(userEntity.getMobile());

        return returnDto;
    }
}