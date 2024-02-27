package org.jetbrains.assignment.domain;

/**
 * Represents the coordinate in the system present in README.md.
 *
 * NOTE: This system seems to be flipped (Y vs. X).
 *
 * @param x The vertical axis. Positive values go North, negative values go South.
 * @param y The horizontal axis. Positive values go East, negative values go West.
 */
public record Coordinate(int x, int y) { }
