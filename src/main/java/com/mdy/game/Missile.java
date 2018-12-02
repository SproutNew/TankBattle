package com.mdy.game;

import javax.swing.*;


class Missile extends MyImage {
    private int direction;
    private final static int speed = 10;
    private final static int damage = 10;
    private int id;

    Missile(int x, int y, int direction, int _id) {
        super(x, y);
        this.height = 10;
        this.width = 10;
        this.direction = direction;
        this.id = _id;
    }

    /**
     * @return 子弹是否碰到物体
     */
    private boolean isMeet() {


        for (Wall wall : Game.walls.values()) {
            if (wall.isIntersects(this)) {
                if (wall.id == Game.WALL) {
                    Game.map[wall.coord.y][wall.coord.x] = Game.BLANK;
                    Game.walls.remove(wall.hashCode());

                }
                return true;
            }
        }

        for (Tank tank : Game.tanks.values()) {
            if (tank.isIntersects(this)) {
                if (id != tank.id) {
                    tank.hp -= damage;
                }
                if (tank.hp <= 0) {
                    //如果该坦克不属于玩家控制的话就不进行下一步的处理
                    if (tank.id >= Game.PLAY_1) {
                        tank.flag = false;
                        if (Game.mode == Mode.Single) {
                            if (tank.equals(Game.tanks.get(tank.hashCode()))) {
                                Game.ShutDown();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, String.valueOf(tank.id == Game.PLAY_1 ? "p2 win!!" : "p2 win!!"));
                            Game.ShutDown();
                        }
                    }
                    Game.map[tank.coord.y][tank.coord.x] = Game.BLANK;
                    tank.flag = false;
                    Game.tanks.remove(tank.hashCode());
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @return 是否碰撞到物体
     */
    boolean Move() {
        if (direction == Game.UP) {
            y -= speed;
            if (isMeet()) {
                return true;
            }
        }
        if (direction == Game.DOWN) {
            y += speed;
            if (isMeet()) {
                return true;
            }
        }
        if (direction == Game.LEFT) {
            x -= speed;
            if (isMeet()) {
                return true;
            }
        }
        if (direction == Game.RIGHT) {
            x += speed;
            return isMeet();
        }
        return false;
    }
}
