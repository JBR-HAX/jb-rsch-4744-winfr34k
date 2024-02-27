package org.jetbrains.assignment;

import org.jetbrains.assignment.domain.Direction;

/**
 * Directions and step-size for the robot to move by.
 */
public record Movement(Direction direction, int steps) {
}
