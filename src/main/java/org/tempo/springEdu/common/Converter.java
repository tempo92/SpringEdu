package org.tempo.springEdu.common;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Converter <Entity, Dto> {

    private Class<Dto> dtoType;

    private final ModelMapper modelMapper = new ModelMapper();

    public Converter (Class<Entity> entityType, Class<Dto> dtoType) {
        this.dtoType = dtoType;
    }

    public List<Dto> entityListToDtoList(List<Entity> users) {
        return users.stream()
                .map(this::entityToDto).collect(Collectors.toList());
    }

    public Dto entityToDto(Entity user) {
        return modelMapper.map(user, dtoType);
    }

}
