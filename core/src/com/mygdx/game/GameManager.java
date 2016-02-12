package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.graphics.g2d.;

import java.util.ArrayList;
/**
 * Created by val on 05/02/2016.
 */
public class GameManager {

    private boolean[][] myGrid;
    private ArrayList<block> myActiveBlocks;
    private ArrayList<ArrayList<block>> otherBlocks;

    int rotation = 0;

    private int sc = 0;


    public boolean[][] getMyGrid() {
        return myGrid;
    }

    public int getScore()
    {
        return sc;
    }

    public GameManager() {
        myGrid = new boolean[10][22];
        myActiveBlocks = new ArrayList<block>();
        otherBlocks = new ArrayList<ArrayList<block>>();
        for (boolean[] line : myGrid) {
            for (boolean b : line) {
                b = false;
            }
        }
    }

    public void generateNextTetrimino() {
        int i = (int) (Math.random() * (8 - 1) + 1);
        rotation = 0;

        switch (i) {
            case 1:
                generateJ();
                addGeneratedBlockToGrid();
                break;
            case 2:
                generateL();
                addGeneratedBlockToGrid();
                break;
            case 3:
                generateO();
                addGeneratedBlockToGrid();
                break;
            case 4:
                generateS();
                addGeneratedBlockToGrid();
                break;
            case 5:
                generateI();
                addGeneratedBlockToGrid();
                break;
            case 6:
                generateZ();
                addGeneratedBlockToGrid();
                break;
            case 7:
                generateT();
                addGeneratedBlockToGrid();
                break;
        }

    }

    public boolean checkGameOver() {
        boolean ch = false;
        for (ArrayList<block> b : otherBlocks) {
            for (block bb : b) {
                if (bb.getX() == 4 && bb.getY() == 0) {
                    ch = true;
                }
            }
        }
        return ch;
    }

    public boolean canMoveDown(boolean[][] grid) {
        boolean ret = true;

        for (block b : myActiveBlocks) {
            grid[b.getX()][b.getY()] = false;
        }

        for (block b : myActiveBlocks) {
            if (grid[b.getX()][b.getY() + 1] == false) {
                ret = ret & true;
            } else {
                ret = ret & false;
            }
        }

        while ( checkLines() )
        {
            checkLines();
        }

        return ret;
    }

    // o,l,t,j,z,s
    public void generateO() {
        myActiveBlocks.clear();
        block b = new block(4, 0, blockType.o);
        myActiveBlocks.add(b);
        b = new block(5, 0, blockType.o);
        myActiveBlocks.add(b);
        b = new block(4, 1, blockType.o);
        myActiveBlocks.add(b);
        b = new block(5, 1, blockType.o);
        myActiveBlocks.add(b);

    }

    public void generateL() {
        myActiveBlocks.clear();
        block b = new block(4, 0, blockType.l);
        myActiveBlocks.add(b);
        b = new block(5, 0, blockType.l);
        myActiveBlocks.add(b);
        b = new block(6, 0, blockType.l);
        myActiveBlocks.add(b);
        b = new block(4, 1, blockType.l);
        myActiveBlocks.add(b);
    }

    public void generateI() {
        myActiveBlocks.clear();
        block b = new block(4, 0, blockType.i);
        myActiveBlocks.add(b);
        b = new block(5, 0, blockType.i);
        myActiveBlocks.add(b);
        b = new block(6, 0, blockType.i);
        myActiveBlocks.add(b);
        b = new block(7, 0, blockType.i);
        myActiveBlocks.add(b);
    }

    public void generateT() {
        myActiveBlocks.clear();
        block b = new block(4, 0, blockType.t);
        myActiveBlocks.add(b);
        b = new block(5, 0, blockType.t);
        myActiveBlocks.add(b);
        b = new block(6, 0, blockType.t);
        myActiveBlocks.add(b);
        b = new block(5, 1, blockType.t);
        myActiveBlocks.add(b);
    }


    public void generateJ() {
        myActiveBlocks.clear();
        block b = new block(4, 0, blockType.j);
        myActiveBlocks.add(b);
        b = new block(5, 0, blockType.j);
        myActiveBlocks.add(b);
        b = new block(6, 0, blockType.j);
        myActiveBlocks.add(b);
        b = new block(6, 1, blockType.j);
        myActiveBlocks.add(b);
    }

    public void generateZ() {
        myActiveBlocks.clear();
        block b = new block(4, 0, blockType.z);
        myActiveBlocks.add(b);
        b = new block(5, 0, blockType.z);
        myActiveBlocks.add(b);
        b = new block(5, 1, blockType.z);
        myActiveBlocks.add(b);
        b = new block(6, 1, blockType.z);
        myActiveBlocks.add(b);

    }

    public void generateS() {
        myActiveBlocks.clear();
        block b = new block(4, 1, blockType.s);
        myActiveBlocks.add(b);
        b = new block(5, 1, blockType.s);
        myActiveBlocks.add(b);
        b = new block(5, 0, blockType.s);
        myActiveBlocks.add(b);
        b = new block(6, 0, blockType.s);
        myActiveBlocks.add(b);

    }

    public void addGeneratedBlockToGrid() {
        for (block b : myActiveBlocks) {
            myGrid[b.getX()][b.getY()] = true;
        }
    }


    public void rotate() {
        ArrayList<block> tmpBlocks = new ArrayList<block>();

        ArrayList<block> cp = (ArrayList<block>) myActiveBlocks.clone();

        tmpBlocks.add(myActiveBlocks.get(0));
        tmpBlocks.add(myActiveBlocks.get(1));
        tmpBlocks.add(myActiveBlocks.get(2));
        tmpBlocks.add(myActiveBlocks.get(3));

        if (myActiveBlocks.get(0).getY() > 1) {
            if (myActiveBlocks.get(0).getBt() != blockType.o) {
                if (rotation == 0) {
                    myActiveBlocks.clear();
                    block b = null;

                    if (tmpBlocks.get(0).getBt() == blockType.l || tmpBlocks.get(0).getBt() == blockType.t || tmpBlocks.get(0).getBt() == blockType.j) {
                        b = new block(tmpBlocks.get(0).getX(), tmpBlocks.get(0).getY() + 2, tmpBlocks.get(0).getBt());
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX() - 1, tmpBlocks.get(1).getY() + 1, tmpBlocks.get(1).getBt());
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX() - 2, tmpBlocks.get(2).getY(), tmpBlocks.get(2).getBt());
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.s) {
                        b = new block(tmpBlocks.get(0).getX() + 1, tmpBlocks.get(0).getY() + 1, blockType.s);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX(), tmpBlocks.get(1).getY(), blockType.s);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX() - 1, tmpBlocks.get(2).getY() + 1, blockType.s);
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.z) {
                        b = new block(tmpBlocks.get(0).getX(), tmpBlocks.get(0).getY() + 2, blockType.z);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX() - 1, tmpBlocks.get(1).getY() + 1, blockType.z);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX(), tmpBlocks.get(2).getY(), blockType.z);
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.i) {
                        b = new block(tmpBlocks.get(0).getX() + 1, tmpBlocks.get(0).getY() + 2, blockType.i);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX(), tmpBlocks.get(1).getY() + 1, blockType.i);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX() - 1, tmpBlocks.get(2).getY(), blockType.i);
                        myActiveBlocks.add(b);
                    }

                    if (tmpBlocks.get(0).getBt() == blockType.l) {
                        b = new block(tmpBlocks.get(3).getX() + 1, tmpBlocks.get(3).getY() + 1, blockType.l);
                    } else if (tmpBlocks.get(0).getBt() == blockType.j || tmpBlocks.get(0).getBt() == blockType.z) {
                        b = new block(tmpBlocks.get(3).getX() - 1, tmpBlocks.get(3).getY() - 1, tmpBlocks.get(3).getBt());
                    } else if (tmpBlocks.get(0).getBt() == blockType.j || tmpBlocks.get(0).getBt() == blockType.t) {
                        b = new block(tmpBlocks.get(3).getX(), tmpBlocks.get(3).getY(), tmpBlocks.get(3).getBt());
                    } else if (tmpBlocks.get(0).getBt() == blockType.s) {
                        b = new block(tmpBlocks.get(3).getX() - 2, tmpBlocks.get(3).getY(), blockType.s);
                    } else if (tmpBlocks.get(0).getBt() == blockType.i) {
                        b = new block(tmpBlocks.get(3).getX() - 2, tmpBlocks.get(3).getY() - 1, blockType.i);
                    }

                    myActiveBlocks.add(b);

                    rotation++;
                } else if (rotation == 1) {
                    myActiveBlocks.clear();
                    block b = null;

                    if (tmpBlocks.get(0).getBt() == blockType.l || tmpBlocks.get(0).getBt() == blockType.j || tmpBlocks.get(0).getBt() == blockType.t) {
                        b = new block(tmpBlocks.get(0).getX() + 2, tmpBlocks.get(0).getY() - 1, tmpBlocks.get(3).getBt());
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX() + 1, tmpBlocks.get(1).getY(), tmpBlocks.get(3).getBt());
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX(), tmpBlocks.get(2).getY() + 1, tmpBlocks.get(3).getBt());
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.s) {
                        b = new block(tmpBlocks.get(0).getX() + 1, tmpBlocks.get(0).getY() - 2, blockType.s);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX(), tmpBlocks.get(1).getY() - 1, blockType.s);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX() + 1, tmpBlocks.get(2).getY(), blockType.s);
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.z) {
                        b = new block(tmpBlocks.get(0).getX() + 2, tmpBlocks.get(0).getY() - 1, blockType.z);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX() + 1, tmpBlocks.get(1).getY(), blockType.z);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX(), tmpBlocks.get(2).getY() - 1, blockType.z);
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.i) {
                        b = new block(tmpBlocks.get(0).getX() + 2, tmpBlocks.get(0).getY() - 3, blockType.i);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX() + 1, tmpBlocks.get(1).getY() - 2, blockType.i);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX(), tmpBlocks.get(2).getY() - 1, blockType.i);
                        myActiveBlocks.add(b);
                    }

                    if (tmpBlocks.get(0).getBt() == blockType.l) {
                        b = new block(tmpBlocks.get(3).getX() + 1, tmpBlocks.get(3).getY() - 2, blockType.l);
                    } else if (tmpBlocks.get(0).getBt() == blockType.j) {
                        b = new block(tmpBlocks.get(3).getX() - 1, tmpBlocks.get(3).getY(), blockType.j);
                    } else if (tmpBlocks.get(0).getBt() == blockType.t) {
                        b = new block(tmpBlocks.get(3).getX(), tmpBlocks.get(3).getY() - 1, blockType.t);
                    } else if (tmpBlocks.get(0).getBt() == blockType.s) {
                        b = new block(tmpBlocks.get(3).getX(), tmpBlocks.get(3).getY() + 1, blockType.s);
                    } else if (tmpBlocks.get(0).getBt() == blockType.z) {
                        b = new block(tmpBlocks.get(3).getX() - 1, tmpBlocks.get(3).getY(), blockType.z);
                    } else if (tmpBlocks.get(0).getBt() == blockType.i) {
                        b = new block(tmpBlocks.get(3).getX() - 1, tmpBlocks.get(3).getY(), blockType.i);
                    }

                    myActiveBlocks.add(b);
                    rotation++;
                } else if (rotation == 2) {
                    myActiveBlocks.clear();
                    block b = null;

                    if (tmpBlocks.get(0).getBt() == blockType.l || tmpBlocks.get(0).getBt() == blockType.j || tmpBlocks.get(0).getBt() == blockType.t) {
                        b = new block(tmpBlocks.get(0).getX(), tmpBlocks.get(0).getY() - 1, tmpBlocks.get(0).getBt());
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX() + 1, tmpBlocks.get(1).getY(), tmpBlocks.get(0).getBt());
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX() + 2, tmpBlocks.get(2).getY() + 1, tmpBlocks.get(0).getBt());
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.s) {
                        b = new block(tmpBlocks.get(0).getX() - 2, tmpBlocks.get(0).getY(), blockType.s);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX() - 1, tmpBlocks.get(1).getY() + 1, blockType.s);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX(), tmpBlocks.get(2).getY(), blockType.s);
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.z) {
                        b = new block(tmpBlocks.get(0).getX() - 1, tmpBlocks.get(0).getY() - 1, blockType.z);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX(), tmpBlocks.get(1).getY(), blockType.z);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX() - 1, tmpBlocks.get(2).getY() + 1, blockType.z);
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.i) {
                        b = new block(tmpBlocks.get(0).getX() - 2, tmpBlocks.get(0).getY(), blockType.i);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX() - 1, tmpBlocks.get(1).getY() + 1, blockType.i);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX(), tmpBlocks.get(2).getY() + 2, blockType.i);
                        myActiveBlocks.add(b);
                    }

                    if (tmpBlocks.get(0).getBt() == blockType.l) {
                        b = new block(tmpBlocks.get(3).getX() - 1, tmpBlocks.get(3).getY(), blockType.l);
                    } else if (tmpBlocks.get(0).getBt() == blockType.j) {
                        b = new block(tmpBlocks.get(3).getX() + 1, tmpBlocks.get(3).getY() + 2, blockType.j);
                    } else if (tmpBlocks.get(0).getBt() == blockType.t) {
                        b = new block(tmpBlocks.get(3).getX(), tmpBlocks.get(3).getY() + 1, blockType.t);
                    } else if (tmpBlocks.get(0).getBt() == blockType.s) {
                        b = new block(tmpBlocks.get(3).getX() + 1, tmpBlocks.get(3).getY() + 1, blockType.s);
                    } else if (tmpBlocks.get(0).getBt() == blockType.z) {
                        b = new block(tmpBlocks.get(3).getX(), tmpBlocks.get(3).getY() + 2, blockType.z);
                    } else if (tmpBlocks.get(0).getBt() == blockType.i) {
                        b = new block(tmpBlocks.get(3).getX() + 1, tmpBlocks.get(3).getY() + 3, blockType.i);
                    }

                    myActiveBlocks.add(b);
                    rotation++;
                } else if (rotation == 3) {
                    myActiveBlocks.clear();
                    block b = null;

                    if (tmpBlocks.get(0).getBt() == blockType.l || tmpBlocks.get(0).getBt() == blockType.j || tmpBlocks.get(0).getBt() == blockType.t) {
                        b = new block(tmpBlocks.get(0).getX() - 2, tmpBlocks.get(0).getY(), tmpBlocks.get(0).getBt());
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX() - 1, tmpBlocks.get(1).getY() - 1, tmpBlocks.get(0).getBt());
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX(), tmpBlocks.get(2).getY() - 2, tmpBlocks.get(0).getBt());
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.s) {
                        b = new block(tmpBlocks.get(0).getX(), tmpBlocks.get(0).getY() + 1, blockType.s);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX() + 1, tmpBlocks.get(1).getY(), blockType.s);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX(), tmpBlocks.get(2).getY() - 1, blockType.s);
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.z) {
                        b = new block(tmpBlocks.get(0).getX() - 1, tmpBlocks.get(0).getY(), blockType.z);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX(), tmpBlocks.get(1).getY() - 1, blockType.z);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX() + 1, tmpBlocks.get(2).getY(), blockType.z);
                        myActiveBlocks.add(b);
                    } else if (tmpBlocks.get(0).getBt() == blockType.i) {
                        b = new block(tmpBlocks.get(0).getX() - 1, tmpBlocks.get(0).getY(), blockType.i);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(1).getX(), tmpBlocks.get(1).getY() - 1, blockType.i);
                        myActiveBlocks.add(b);
                        b = new block(tmpBlocks.get(2).getX() + 1, tmpBlocks.get(2).getY() - 2, blockType.i);
                        myActiveBlocks.add(b);
                    }

                    if (tmpBlocks.get(0).getBt() == blockType.l) {
                        b = new block(tmpBlocks.get(3).getX() - 1, tmpBlocks.get(3).getY() + 1, blockType.l);
                    } else if (tmpBlocks.get(0).getBt() == blockType.j) {
                        b = new block(tmpBlocks.get(3).getX() + 1, tmpBlocks.get(3).getY() - 1, blockType.j);
                    } else if (tmpBlocks.get(0).getBt() == blockType.t) {
                        b = new block(tmpBlocks.get(3).getX(), tmpBlocks.get(3).getY(), blockType.t);
                    } else if (tmpBlocks.get(0).getBt() == blockType.s) {
                        b = new block(tmpBlocks.get(3).getX() + 1, tmpBlocks.get(3).getY() - 2, blockType.s);
                    } else if (tmpBlocks.get(0).getBt() == blockType.z) {
                        b = new block(tmpBlocks.get(3).getX() + 2, tmpBlocks.get(3).getY() - 1, blockType.z);
                    } else if (tmpBlocks.get(0).getBt() == blockType.i) {
                        b = new block(tmpBlocks.get(3).getX() + 2, tmpBlocks.get(3).getY() - 3, blockType.i);
                    }

                    myActiveBlocks.add(b);
                    rotation = 0;
                }
            }
        }

        boolean a = false;

        for (block b : myActiveBlocks) {
            if (b.getX() >= 10) {
                a = true;
            }
        }

        if (a) {
            myActiveBlocks = cp;
            switch (rotation) {
                case 0:
                    rotation = 3;
                    break;
                case 1:
                    rotation = 0;
                    break;
                case 2:
                    rotation = 1;
                    break;
                case 3:
                    rotation = 2;
                    break;
            }
        } else {
            int i = 0;
            for (block b : cp) {
                myGrid[b.getX()][b.getY()] = false;
                b.PrintBlockXY();
                i++;
            }
            System.out.println(i);
            printGrid();

            for (block b : myActiveBlocks) {
                myGrid[b.getX()][b.getY()] = true;
            }
        }
    }

    public void moveRight(boolean[][] grd) {
        boolean k = true;

        // check border
        for (block b : myActiveBlocks) {
            if (b.getX() >= 9) {
                k = false;
            }
        }
        if (k) {
            // clean tetrimino from grid
            for (block b : myActiveBlocks) {
                grd[b.getX()][b.getY()] = false;
            }

            // check if it can get on the right
            for (block b : myActiveBlocks) {
                k = k & !grd[b.getX() + 1][b.getY()];
            }

            // if not against border and can move to the right move it
            if (k) {
                // cleaning it from the actual grid
                for (block b : myActiveBlocks) {
                    myGrid[b.getX()][b.getY()] = false;
                }
                // updating its position on the grid and on the arraylist
                for (block b : myActiveBlocks) {
                    myGrid[b.getX() + 1][b.getY()] = true;
                    b.setX(b.getX() + 1);
                }
            }
        }
    }

    public void moveLeft(boolean[][] grd) {
        boolean k = true;

        // check border
        for (block b : myActiveBlocks) {
            if (b.getX() == 0) {
                k = false;
            }
        }

        if (k) {
            // clean tetrimino from grid
            for (block b : myActiveBlocks) {
                grd[b.getX()][b.getY()] = false;
            }

            // check if it can get on the left
            for (block b : myActiveBlocks) {
                k = k & !grd[b.getX() - 1][b.getY()];
            }

            // if not against border and can move to the right move it
            if (k) {
                // cleaning it from the actual grid
                for (block b : myActiveBlocks) {
                    myGrid[b.getX()][b.getY()] = false;
                }
                // updating its position on the grid and on the arraylist
                for (block b : myActiveBlocks) {
                    myGrid[b.getX() - 1][b.getY()] = true;
                    b.setX(b.getX() - 1);
                }
            }
        }
    }

    public void moveBlockDown() {
        if (canMoveDown(myGrid)) {

            if (!checkBorderDown()) {
                for (block b : myActiveBlocks) {
                    myGrid[b.getX()][b.getY()] = false;
                }
                for (block b : myActiveBlocks) {
                    myGrid[b.getX()][b.getY() + 1] = true;
                    b.setY(b.getY() + 1);
                }
            } else {
                ArrayList<block> ba = copyToOthers(myActiveBlocks);
                otherBlocks.add(ba);
                updateOthersToGrid();

                generateNextTetrimino();
            }
        } else {
            sc += 10;
            ArrayList<block> ba = copyToOthers(myActiveBlocks);
            otherBlocks.add(ba);
            updateOthersToGrid();

            while (checkLines())
            {
                checkLines();
            }
            generateNextTetrimino();
        }
    }


    public void updateOthersToGrid() {
        for (ArrayList<block> t : otherBlocks) {
            for (block b : t) {
                if (b.getX() >= 0) {
                    myGrid[b.getX()][b.getY()] = true;
                }
            }
        }
    }

    public ArrayList<block> copyToOthers(ArrayList<block> ab) {
        ArrayList<block> cb = new ArrayList<block>();
        for (block b : ab) {
            cb.add(b);
        }
        return cb;
    }

    public boolean checkBorderDown() {
        boolean b = false;
        for (block ab : myActiveBlocks) {
            if (ab.getY() == 20) {
                b = true;
                break;
            }
        }
        return b;
    }

    public void printCurrentT(ShapeRenderer mySR) {

        /*mySR.begin(ShapeRenderer.ShapeType.Filled);
        mySR.setColor(myActiveBlocks.get(0).getC());*/

        SpriteBatch bat = new SpriteBatch();
        bat.begin();

        for (block b : myActiveBlocks) {
            if (b.getX() >= 0) {

                if (b.getBt() == blockType.l) {
                    bat.draw(block.getL(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                } else if (b.getBt() == blockType.j) {
                    bat.draw(block.getJ(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                } else if (b.getBt() == blockType.i) {
                    bat.draw(block.getI(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                } else if (b.getBt() == blockType.t) {
                    bat.draw(block.getT(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                } else if (b.getBt() == blockType.o) {
                    bat.draw(block.getO(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                } else if (b.getBt() == blockType.s) {
                    bat.draw(block.getS(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                } else if (b.getBt() == blockType.z) {
                    bat.draw(block.getZ(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                }

                //mySR.rect(140 + b.getX() * 40, 940 - b.getY() * 40, 40, 40);
            }
        }

        bat.end();
        //mySR.end();
    }

    public void clearOther()
    {
        if ( otherBlocks.size() > 0)
        {
            boolean b, cleaning;

            cleaning = true;

            // tant qu'on nettoie
            while (cleaning) {
                // parcours des bloques posés
                for (ArrayList<block> ar : otherBlocks) {
                    b = true;
                    for (block bl : ar) {
                        // le x = -1 veut dire que le bloque a déjà été détruit
                        // si tous les blocks ont un x à -1 on enlève la ligne du tableau
                        if (bl.getX() == -1) {
                            b = b & true;
                        } else {
                            b = b & false;
                            cleaning = false;
                        }
                    }

                    // si on a un tetrimino mort
                    if (b) {
                        // on regarde s'il s'agit du dernier tetriminos dans l'arraylist
                        // si on on arrête le cleaning
                        if (otherBlocks.indexOf(ar) == otherBlocks.size() - 1) {
                            cleaning = false;
                        }

                        // on détruit ce tetrimino définitivement
                        otherBlocks.remove(ar);
                        //on sort de la boucle for pour ne pas causer d'exception à la con
                        break;
                    }
                }
            }
        }
    }


    public void printOtherT(ShapeRenderer mySR){

        SpriteBatch bat = new SpriteBatch();
        bat.begin();

        //mySR.begin(ShapeRenderer.ShapeType.Filled);
        //   System.out.println("in 1");
        for(ArrayList<block> T : otherBlocks){
            //     System.out.println("in 2");
            mySR.setColor(T.get(0).getC());
            for(block b : T){
                //       System.out.println("in 3");
                if(b.getX() >= 0 && b.getY() >= 0){
                    if( b.getBt() == blockType.l )
                    {
                        bat.draw(block.getL(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                    }
                    else if( b.getBt() == blockType.j )
                    {
                        bat.draw(block.getJ(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                    }
                    else if( b.getBt() == blockType.i )
                    {
                        bat.draw(block.getI(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                    }
                    else if( b.getBt() == blockType.t )
                    {
                        bat.draw(block.getT(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                    }
                    else if( b.getBt() == blockType.o )
                    {
                        bat.draw(block.getO(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                    }
                    else if( b.getBt() == blockType.s )
                    {
                        bat.draw(block.getS(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                    }
                    else if( b.getBt() == blockType.z )
                    {
                        bat.draw(block.getZ(), 140 + b.getX() * 40, 940 - b.getY() * 40, 40,40);
                    }
                    //mySR.rect(140 + b.getX() * 40, 940 - b.getY() * 40, 40, 40);
                }
                // b.PrintBlockXY();
            }
        }

        bat.end();
        //mySR.end();
    }


    public void printGrid(){
        for(int i =0; i <= 20; i++ ){
            for(int j =0; j <= 9; j++){
                if(myGrid[j][i]){
                    System.out.print("1");
                }else{
                    System.out.print("0");
                }
            }
            System.out.println();
        }
        System.out.println("----------");
    }

    public boolean checkLines(){
        int i = 0;
        boolean lineFound = false;

        for (int j = 0; j < 21; j++)
        {
            if(myGrid[0][j] & myGrid[1][j] & myGrid[2][j] & myGrid[3][j] & myGrid[4][j] & myGrid[5][j] & myGrid[6][j] & myGrid[7][j] & myGrid[8][j] & myGrid[9][j]){
                lineFound = true;
                break;
            }
            i++;
        }
        if(lineFound){

            printGrid();
            for(int j = i; j > 0; j--){
                myGrid[0][j] = myGrid[0][j-1];
                myGrid[1][j] = myGrid[1][j-1];
                myGrid[2][j] = myGrid[2][j-1];
                myGrid[3][j] = myGrid[3][j-1];
                myGrid[4][j] = myGrid[4][j-1];
                myGrid[5][j] = myGrid[5][j-1];
                myGrid[6][j] = myGrid[6][j-1];
                myGrid[7][j] = myGrid[7][j-1];
                myGrid[8][j] = myGrid[8][j-1];
                myGrid[9][j] = myGrid[9][j-1];
            }

            int a =0;

            for(ArrayList<block> ob : otherBlocks){
                for(block b : ob){
                    if(b.getY() == i){
                        b.setX(-1);
                    }else{
                        if(b.getY() < i && b.getX() != -1){
                            b.setY(b.getY() + 1);
                        }
                    }
                }
                a++;
            }
            sc += 100;

            clearOther();
        }

        return lineFound;
    }
}
