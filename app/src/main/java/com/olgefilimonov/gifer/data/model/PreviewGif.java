package com.olgefilimonov.gifer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreviewGif {

  @SerializedName("url") @Expose private String url;
  @SerializedName("width") @Expose private String width;
  @SerializedName("height") @Expose private String height;
  @SerializedName("size") @Expose private String size;
}
