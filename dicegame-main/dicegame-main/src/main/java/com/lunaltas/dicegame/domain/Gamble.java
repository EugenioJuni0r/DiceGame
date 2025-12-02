package com.lunaltas.dicegame.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@SuppressWarnings("serial")
@Entity
@Table(name="gamble")
public class Gamble extends AbstractEntity<Long> {
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name="bet_id")
  private Bet bet;

  @NotNull(message = "O valor é obrigatório.")
  @Min(value = 0, message = "O valor deve ser maior ou igual a 0")
  @Column(name = "value", nullable = false)
  private Float value;

  @NotNull(message= "O palpite é obrigatório.")
  @Min(value = 1, message = "O palpite deve ser entre 1 e 6")
  @Max(value = 6, message = "O palpite deve ser entre 1 e 6")
  @Column(name="user_guess", nullable=false)
  private Integer user_guess;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Bet getBet() {
    return bet;
  }

  public void setBet(Bet bet) {
    this.bet = bet;
  }

  public Float getValue() {
    return value;
  }

  public void setValue(Float value) {
    this.value = value;
  }

  public Integer getUser_guess() {
    return user_guess;
  }

  public void setUser_guess(Integer user_guess) {
    this.user_guess = user_guess;
  }
  
}
