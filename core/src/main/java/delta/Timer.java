package delta;

import com.badlogic.gdx.Gdx;

public class Timer {
    public boolean repeat = false;
    public float time;
    public float delay = 0;
    public Runnable onTriggerd = () -> {
    };

    private boolean running = false;
    private float remainingTime = 0;
    private float remainingDelay = 0;

    public Timer(float time) {
        this.time = time;
    }

    public Timer start() {
        this.running = true;
        this.remainingTime = this.time;
        this.remainingDelay = this.delay;

        return this;
    }

    public Timer stop() {
        this.running = false;

        return this;
    }

    private void finished() {
        if (!this.repeat) {
            this.running = false;
        }
        this.onTriggerd.run();
    }

    public void update() {
        if (!running) {
            return;
        }

        float delta = Gdx.graphics.getDeltaTime();

        if (this.remainingDelay > 0) {
            this.remainingDelay -= delta;
        } else {
            this.remainingTime -= delta;
            if (this.remainingTime <= 0) {
                this.finished();
            }
        }
    }

    public boolean isRunning() {
        return this.running;
    }

    public boolean isFinished() {
        return !this.running && this.remainingTime <= 0;
    }

    public float getProgress() {
        return 1f - Math.max(this.remainingTime, 0) / this.time;
    }

    public Timer setOnTriggered(Runnable r) {
        this.onTriggerd = r;
        return this;
    }

    public Timer repeating() {
        this.repeat = true;
        return this;
    }

    public Timer setDelay(float d) {
        this.delay = d;
        return this;
    }
}
