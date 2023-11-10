package com.healthcare.userservice.service.implementation;

import com.healthcare.userservice.constants.AppConstants;
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
        doctorEntity.setRoomNo(doctorDto.getRoomNo());

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
}
