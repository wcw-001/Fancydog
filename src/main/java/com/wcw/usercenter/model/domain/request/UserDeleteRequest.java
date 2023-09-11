package com.wcw.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserDeleteRequest implements Serializable {
  private Long id;
  private static final long serialVersionUID = -4356425365213683936L;

}
