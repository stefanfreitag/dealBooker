package de.freitag.stefan.alexa.dealbooker;

import com.google.common.base.Enums;
import java.util.Objects;
import java.util.Optional;

enum Unit {
  @SuppressWarnings("unused")
  GIGAWATT,
  @SuppressWarnings("unused")
  MEGAWATT,
  @SuppressWarnings("unused")
  KILOWATT,
  @SuppressWarnings("unused")
  WATT;

  static Optional<Unit> from(final String text) {
    Objects.requireNonNull(text);
    return Enums.getIfPresent(Unit.class, text).toJavaUtil();
  }
}
