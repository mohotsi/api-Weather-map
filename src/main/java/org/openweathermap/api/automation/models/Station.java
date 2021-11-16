package org.openweathermap.api.automation.models;

import com.google.gson.annotations.SerializedName;
import lombok.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Station {

 @SerializedName(value = "ID", alternate = {"id"})
     private String ID;
      private String updated_at;
      private String created_at;
      private String user_id;
      private String external_id;
      private String name;
      private Double latitude;
      private Double longitude;
      private  Double altitude;
      private Integer rank;
      private Integer source_type;


@Override
 public boolean equals(Object that){
 if(!(that instanceof Station)) return false;
 val station= (Station) that;
 return   (ID.equals(station.getID()))&&
         external_id.equals(station.external_id)&&
         name.equals(station.name)&&
         longitude.equals(station.longitude)&&
        latitude.equals(station.latitude)&&
         altitude.equals(station.altitude);

}
 public boolean equalsIgnoreIDs(Object that){
  if(!(that instanceof Station)) return false;
  val station= (Station) that;
  return
          external_id.equals(station.external_id)&&
          name.equals(station.name)&&
          longitude.equals(station.longitude)&&
          latitude.equals(station.latitude)&&
          altitude.equals(station.altitude);

 }








}
