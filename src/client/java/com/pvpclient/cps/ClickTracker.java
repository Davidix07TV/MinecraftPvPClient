package com.pvpclient.cps;

import org.lwjgl.glfw.GLFW;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Traccia i click del mouse con timestamp precisi e calcola CPS
 * (Clicks Per Second) su una finestra scorrevole di 1000 ms.
 * <p>
 * Solo conteggio passivo — nessun input simulato (no auto-clicker).
 */
public final class ClickTracker {
	public static final ClickTracker INSTANCE = new ClickTracker();

	private static final long WINDOW_MS = 1000L;

	private final Deque<Long> leftClicks = new ArrayDeque<>();
	private final Deque<Long> rightClicks = new ArrayDeque<>();

	private ClickTracker() {
	}

	public void registerClick(int button) {
		long now = System.currentTimeMillis();

		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			leftClicks.addLast(now);
			pruneOlderThan(leftClicks, now);
		} else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			rightClicks.addLast(now);
			pruneOlderThan(rightClicks, now);
		}
	}

	public int getLeftCps() {
		return countRecent(leftClicks);
	}

	public int getRightCps() {
		return countRecent(rightClicks);
	}

	public int getTotalCps() {
		return getLeftCps() + getRightCps();
	}

	private int countRecent(Deque<Long> clicks) {
		long now = System.currentTimeMillis();
		pruneOlderThan(clicks, now);
		return clicks.size();
	}

	private void pruneOlderThan(Deque<Long> clicks, long now) {
		long cutoff = now - WINDOW_MS;
		while (!clicks.isEmpty() && clicks.peekFirst() < cutoff) {
			clicks.removeFirst();
		}
	}
}
