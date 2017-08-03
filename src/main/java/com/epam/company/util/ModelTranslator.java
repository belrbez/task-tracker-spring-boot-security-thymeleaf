package com.epam.company.util;


import com.epam.company.model.dao.Participant;
import com.epam.company.model.dao.Task;
import com.epam.company.model.dao.User;
import com.epam.company.model.dto.TaskDTO;
import com.epam.company.model.dto.UserDTO;
import com.epam.company.model.dto.UserFormDTO;
import com.epam.company.model.types.UserProfileType;
import com.epam.company.model.util.TaskModification;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by @belrbeZ
 *
 * Translate and full fill DTO object to DAO object and revert
 */
public class ModelTranslator {

    //<editor-fold desc="toDTO">

    public static TaskModification toDTO(Participant model) {
        return (model == null)
                ? TaskModification.EMPTY
                : new TaskModification(model.getId(),
                                        model.getTaskId(),
                                        model.getModifyCounter(),
                                        model.getLastModifyDate().toLocalDateTime());
    }

    public static TaskDTO toDTO(Task model) {
        return (model == null)
                ? TaskDTO.EMPTY
                : new TaskDTO(model.getId(),
                            model.getTopicStarterId(),
                            model.getType(),
                            model.getTheme(),
                            model.getDescr(),
                            model.getState(),
                            model.getDeadline(),
                            model.getCreateDate(),
                            model.getLastModifyDate());
    }


    public static UserDTO toDTO(User model) {
        return (model == null)
                ? UserDTO.EMPTY
                : new UserDTO(model.getId(),
                            model.getSsoId(),
                            model.getPassword(),
                            model.getFirstName(),
                            model.getLastName(),
                            model.getEmail(),
                            model.getAbout(),
                            model.getPhone(),
                            model.getCity(),
                            model.getState(),
                            model.getUserProfileType(),
                            model.getEmployeeRole());
    }

    //</editor-fold>

    //<editor-fold desc="toListsDTO">

    public static List<UserDTO> usersToDTO(List<User> models) {
        return (models == null)
                ? Collections.emptyList()
                : models.stream().map(ModelTranslator::toDTO).collect(Collectors.toList());
    }

    public static List<TaskDTO> tasksToDTO(List<Task> models) {
        return (models == null)
                ? Collections.emptyList()
                : models.stream().map(ModelTranslator::toDTO).collect(Collectors.toList());
    }

    //</editor-fold>

    //<editor-fold desc="toDAO">

    public static Participant toDAO(TaskModification model, Long userId) {
        if(model == null)
            throw new NullPointerException("MODEL");

        if(Validator.isIdValid(userId))
            throw new NullPointerException("USER ID");

        return new Participant(model, userId);

    }

    public static User toDAO(UserDTO model) {
        if(model == null)
            throw new NullPointerException();

        return new User(model.getSsoId(),
                        model.getPassword(),
                        model.getFirstName(),
                        model.getLastName(),
                        model.getEmail(),
                        model.getPhone(),
                        model.getCity(),
                        model.getState(),
                        model.getUserProfileType(),
                        model.getEmployeeRole());
    }

    public static User toDAO(UserFormDTO model) {
        if(model == null)
            throw new NullPointerException();

        return new User(UserProfileType.USER,
                        model.getSsoId(),
                        model.getPassword(),
                        model.getEmail());
    }

    public static Task toDAO(TaskDTO model) {
        if(model == null)
            throw new NullPointerException();
        return new Task(model.getTopicStarterId(),
                    model.getType(),
                    model.getTheme(),
                    model.getDescr(),
                    Timestamp.valueOf(model.getDeadline()!=null ? model.getDeadline() : LocalDateTime.now().plusDays(60)));
    }

    //</editor-fold>

    //<editor-fold desc="DAO update strategy">

    public static User updateDAO(User dao, UserDTO dto) throws NullPointerException, NumberFormatException {
        if(dto == null)
            throw new NullPointerException("NULLABLE dto");

        if(dao == null)
            throw new NullPointerException("NULLABLE dao");

        if(!Objects.equals(dao.getId(), dto.getId()))
            throw new NumberFormatException("DAO id differ from DTO id");

        dao.setEmail(Validator.isEmailValid(dto.getEmail()) ? dto.getEmail() : dao.getEmail());
        dao.setFirstName(!Validator.isStrEmpty(dto.getFirstName()) ? dto.getFirstName() : dao.getFirstName());
        dao.setAbout(!Validator.isStrEmpty(dto.getAbout()) ? dto.getAbout() : dao.getAbout());
        dao.setPhone(Validator.isPhoneValid(dto.getPhone()) ? dto.getPhone() : dao.getPhone());
        dao.setPassword(Validator.isPassValid(dto.getPassword()) ? dto.getPassword() : dao.getPassword());
        dao.setUserProfileType((dto.getUserProfileType() != null) ? dto.getUserProfileType() : dao.getUserProfileType());
//        dao.set((dto.getMuteEnd() != null && dto.getMuteEnd().isAfter(LocalDateTime.now())) ? Timestamp.valueOf(dto.getMuteEnd()) : dao.getMuteEnd());
        dao.setCity(dto.getCity() != null ? dto.getCity() : dao.getCity());
        dao.setLastName(dto.getLastName() != null ? dto.getLastName() : dao.getLastName());
        dao.setState(dto.getState() != null ? dto.getState() : dao.getState());
        dao.setEmployeeRole(dto.getEmployeeRole() != null ? dto.getEmployeeRole() : dao.getEmployeeRole());

        return dao;
    }


    public static Task updateDAO(Task dao, TaskDTO dto) throws NullPointerException, NumberFormatException {
        if(dto == null)
            throw new NullPointerException("NULLABLE dto");

        if(dao == null)
            throw new NullPointerException("NULLABLE dao");

        if(!Objects.equals(dao.getId(), dto.getId()))
            throw new NumberFormatException("DAO id differ from DTO id");

        if(!Objects.equals(dao.getTopicStarterId(), dto.getTopicStarterId()))
            throw new NumberFormatException("DAO TopicStarterId differ from DTO TopicStarterId");

        dao.setCompanyStarterId((dto.getCompanyStarterId() != null) ? dto.getCompanyStarterId() : dao.getCompanyStarterId());
        dao.setDescr((!Validator.isStrEmpty(dto.getDescr())) ? dto.getDescr() : dao.getDescr());
        dao.setTheme((!Validator.isStrEmpty(dto.getTheme())) ? dto.getTheme() : dao.getDescr());
        dao.setType((dto.getType() != null) ? dto.getType() : dao.getType());
        dao.setDeadline((dto.getDeadline() != null) ? Timestamp.valueOf(dto.getDeadline()) : dao.getDeadline());

        return dao;
    }

    //</editor-fold>
}
