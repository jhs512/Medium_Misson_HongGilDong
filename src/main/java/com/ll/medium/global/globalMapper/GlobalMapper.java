package com.ll.medium.global.globalMapper;

import com.ll.medium.standard.base.HasConcreteDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalMapper {
    @Autowired
    @Lazy
    private GlobalMapper self;
    private final ModelMapper modelMapper;

    @SneakyThrows
    public <T> T map(Object source, Class<T> type) {
        if (type.isInterface()) {
            type = self.getConcreteType(type);
        }
        return modelMapper.map(source, type);
    }

    @SneakyThrows
    @Cacheable(value = "GlobalMapper.getConcreteType", key = "{#type.getName()}")
    public <T> Class<T> getConcreteType(Class<T> type) {
        if (HasConcreteDto.class.isAssignableFrom(type)) {
            return (Class<T>) Class.forName(type.getName() + "Impl");
        }
        return type;
    }
}
