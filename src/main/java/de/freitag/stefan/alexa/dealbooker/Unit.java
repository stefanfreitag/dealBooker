package de.freitag.stefan.alexa.dealbooker;

import com.google.common.base.Enums;
import java.util.Optional;
import lombok.NonNull;

enum Unit {
  @SuppressWarnings("unused")
  GIGAWATT,
  @SuppressWarnings("unused")
  MEGAWATT,
  @SuppressWarnings("unused")
  KILOWATT,
  @SuppressWarnings("unused")
  WATT;

  static Optional<Unit> from(@NonNull final String text) {
    return Enums.getIfPresent(Unit.class, text).toJavaUtil();
  }
}
