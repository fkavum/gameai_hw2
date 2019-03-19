package com.cin_damasi.app.chinese_checker_package;

import java.awt.*;
import javax.swing.*;

import javax.swing.JButton;



public class PieceGUI
{
    private JButton parentButton;
    private JButton button;
    private Image buttonImage;
    private Piece piece;

    private static int idCounter = 0;

    private int id = 0;

    public PieceGUI(JButton parentButton, Piece piece, Image buttonImage)
    {
        this.parentButton = parentButton;
        this.piece = piece;
        this.button = new RoundButton("");
        
        parentButton.setLayout(new BorderLayout());
        parentButton.add(this.button, BorderLayout.CENTER);

        if (buttonImage != null)
        {
            this.buttonImage = buttonImage;
            this.button.setIcon(new ImageIcon(this.buttonImage.getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)));
        }

        this.id = PieceGUI.idCounter;
        PieceGUI.idCounter = PieceGUI.idCounter + 1;
        //System.out.println("PieceGUI id counter:" + PieceGUI.idCounter);
    }

    //  draw piece inside (center of) given component
    public void drawToParentComponent(Component parentComponent)
    {
        int parentWidth = parentComponent.getWidth();
        int parentHeight = parentComponent.getHeight();

        //  take minimum of width and height
        int newRadius = Math.min(parentWidth, parentHeight);

        //  padding by 4 pixels
        int pieceWidth = newRadius - 4;
        int pieceHeight = newRadius - 4;
        this.button.setSize(pieceWidth, pieceHeight);
        
        //  relocate button to center of parent
        int cx = (parentWidth - pieceWidth) / 2;
        int cy = (parentHeight - pieceHeight) / 2;
        this.button.setLocation(cx, cy);

        //  resize icon
        this.button.setIcon(new ImageIcon(this.buttonImage.getScaledInstance(pieceWidth, pieceHeight, java.awt.Image.SCALE_SMOOTH)));
    }

    //  used when parent of the piece button resized
    public void ResizeWithParent()
    {
        this.drawToParentComponent((Component)this.parentButton);
    }

    public String toString()
    {
        return "Piece, id:" + this.id + ", position:" + this.getPosition()
                + ", color:" + Integer.toString(this.getColor());
    }

    public int getColor()
    {
        return this.piece.getColor();
    }

    public PiecePosition getPosition()
    {
        return this.piece.getPosition();
    }

    public JButton getButton()
    {
        return this.button;
    }

    public JButton getParentButton()
    {
        return this.parentButton;
    }

    public Piece getPiece()
    {
        return this.piece;
    }

    public void setParentButton(JButton newParent)
    {
        //  remove this button from parent
        this.parentButton.remove(this.button);

        this.parentButton = newParent;

        //  attach to new parent
        this.parentButton.setLayout(new BorderLayout());
        this.parentButton.add(this.button, BorderLayout.CENTER);

    }

}
