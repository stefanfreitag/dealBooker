package de.freitag.stefan.alexa.dealbooker;

import com.google.common.base.Enums;
import java.util.Optional;
import lombok.NonNull;

enum DealType {
  BUY,
  SELL;

  static Optional<DealType> from(@NonNull final String text) {
    return Enums.getIfPresent(DealType.class, text).toJavaUtil();
  }
}
