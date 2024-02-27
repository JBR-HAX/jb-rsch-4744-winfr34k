package org.jetbrains.assignment.domain;

/**
 * Directions and step-size for the robot to move by.
 */
public record Move(Direction direction, int steps) {
}
