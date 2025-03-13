package com.alvarohdezarroyo.lookmomicanfly.DTO;

import com.alvarohdezarroyo.lookmomicanfly.Models.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String id, name, category, subcategory, manufacturer;
    private Integer releaseYear;
    private Boolean active;
    private String [] colors;

    public void setColorList(List<Color> colorList){
        colors=new String[colorList.size()];
        int position=0;
        for (Color color: colorList) {
            colors[position]=color.getName();
            position++;
        }
    }

}
