package org.jetbrains.assignment.domain;

/**
 * Represents the coordinate in the system present in README.md.
 *
 * NOTE: Unlike in the README, this coordinate system is un-flipped.
 *
 * @param x The horizontal axis. Positive values go East, negative values go West.
 * @param y The vertical axis. Positive values go North, negative values go South.
 */
public record Location(int x, int y) { }
