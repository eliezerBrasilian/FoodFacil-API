package com.br.foodfacil.models;

import com.br.foodfacil.enums.OnlineStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "online")
public class Online {
    @Id
    private String id;
    private OnlineStatus onlineStatus;


}
