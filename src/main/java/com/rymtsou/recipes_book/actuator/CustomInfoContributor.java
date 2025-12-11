package com.rymtsou.recipes_book.actuator;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Component
public class CustomInfoContributor implements InfoContributor {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm::ss.SSS")
    private final ZonedDateTime startupTime = ZonedDateTime.now(ZoneId.of(ZoneId.systemDefault().getId()));


    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("startupTime", startupTime.toString());
    }
}
