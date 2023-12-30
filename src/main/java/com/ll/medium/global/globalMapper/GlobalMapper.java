package com.ll.medium.global.globalMapper;

import com.ll.medium.standard.base.HasConcreteDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalMapper {
    private final ModelMapper modelMapper;

    @SneakyThrows
    public <T> T map(Object source, Class<T> type) {
        if (type.isInterface() && HasConcreteDto.class.isAssignableFrom(type))
            type = (Class<T>) Class.forName(type.getName() + "Impl");

        return modelMapper.map(source, type);
    }
}
