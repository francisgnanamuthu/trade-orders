package com.fcr.trade.orders.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Order
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-08-01T22:45:41.240-04:00[America/New_York]")

public class Order  implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Gets or Sets side
   */
  public enum SideEnum {
    BUY("Buy"),
    
    SELL("Sell");

    private String value;

    SideEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static SideEnum fromValue(String value) {
      for (SideEnum b : SideEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("side")
  private SideEnum side;

  @JsonProperty("security")
  private String security;

  @JsonProperty("fund-name")
  private String fundName;

  @JsonProperty("quantity")
  private Long quantity;

  @JsonProperty("price")
  private Integer price;

  public Order side(SideEnum side) {
    this.side = side;
    return this;
  }

  /**
   * Get side
   * @return side
  */
  @ApiModelProperty(example = "Buy", required = true, value = "")
  @NotNull


  public SideEnum getSide() {
    return side;
  }

  public void setSide(SideEnum side) {
    this.side = side;
  }

  public Order security(String security) {
    this.security = security;
    return this;
  }

  /**
   * Get security
   * @return security
  */
  @ApiModelProperty(example = "AAPL", required = true, value = "")
  @NotNull

@Size(min=1,max=250) 
  public String getSecurity() {
    return security;
  }

  public void setSecurity(String security) {
    this.security = security;
  }

  public Order fundName(String fundName) {
    this.fundName = fundName;
    return this;
  }

  /**
   * Get fundName
   * @return fundName
  */
  @ApiModelProperty(example = "MAG", required = true, value = "")
  @NotNull

@Size(min=1,max=250) 
  public String getFundName() {
    return fundName;
  }

  public void setFundName(String fundName) {
    this.fundName = fundName;
  }

  public Order quantity(Long quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
  */
  @ApiModelProperty(example = "1000", required = true, value = "")
  @NotNull


  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Order price(Integer price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
  */
  @ApiModelProperty(example = "700", required = true, value = "")
  @NotNull


  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(this.side, order.side) &&
        Objects.equals(this.security, order.security) &&
        Objects.equals(this.fundName, order.fundName) &&
        Objects.equals(this.quantity, order.quantity) &&
        Objects.equals(this.price, order.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(side, security, fundName, quantity, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Order {\n");
    
    sb.append("    side: ").append(toIndentedString(side)).append("\n");
    sb.append("    security: ").append(toIndentedString(security)).append("\n");
    sb.append("    fundName: ").append(toIndentedString(fundName)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

