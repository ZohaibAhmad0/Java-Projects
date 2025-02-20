package main;

import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Board extends JPanel {
    public int tileSize = 85;

    int rows = 8;
    int cols = 8;

    ArrayList<Piece> pieceList = new ArrayList<>();

    public Piece selectedPiece;

    input input = new input(this);

    public int enPassantTile = -1;

    ChessClock chessClock;

    public boolean isWhiteToMove = true;
    private boolean isGameOver = false;

    public CheckScanner checkScanner = new CheckScanner(this);


    public Board(){
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));

        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        addPieces();
    }

    public Piece getPiece(int col, int row) {

        for( Piece piece : pieceList ){
            if(piece.col == col && piece.row == row){
                return piece;
            }
        }
        return null;
    }

    public boolean isValidMove(Move move)
    {
        if(isGameOver){
            return false;
        }

        if(move.piece.isWhite != isWhiteToMove){
            return false;
        }

        if(sameTeam(move.piece, move.capture)) {
            return false;
        }

        if(!move.piece.isValidMovement(move.newCol, move.newRow)){
            return false;
        }

        if(move.piece.moveCollidesWithPiece(move.newCol, move.newRow)){
            return false;
        }

        if(checkScanner.isKingChecked(move)){
            return false;
        }

        //chessClock.switchTurns();

        return true;
    }

    public void makeMove(Move move) {

        if(move.piece.name.equals("Pawn")){
            movePawn(move);
        } else if(move.piece.name.equals("King")) {
            moveKing(move);
        }

            move.piece.col = move.newCol;
            move.piece.row = move.newRow;
            move.piece.xPos = move.newCol * tileSize;
            move.piece.yPos = move.newRow * tileSize;

            move.piece.iSFirstMove = false;

            capture(move.capture);

            isWhiteToMove = !isWhiteToMove;

            updateGameStatus();
        }

    private void moveKing(Move move){
        if(Math.abs(move.piece.col - move.newCol) == 2 ){
            Piece rook;
            if(move.piece.col < move.newCol){
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            }else {
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.xPos = rook.col * tileSize;
        }

    }

    private void movePawn(Move move) {

        // en Passant
        int colorIndex = move.piece.isWhite ? 1 : -1;

        if(getTileNum(move.newCol, move.newRow) == enPassantTile){
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        }

        if(Math.abs(move.piece.row - move.newRow) == 2){
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        }else {
            enPassantTile = -1;
        }

        //promotions
        colorIndex = move.piece.isWhite ? 0 : 7;
        if(move.newRow == colorIndex){
            promotePawn(move);
        }

    }

    private void promotePawn(Move move) {
        pieceList.add(new Queen(this,move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece) {

        pieceList.remove(piece);

    }

    public boolean sameTeam(Piece p1, Piece p2) {

        if(p1 == null || p2 == null) {
            return false;
        }

        return p1.isWhite == p2.isWhite;
    }

    Piece findKing(boolean isWhite){
        for(Piece piece: pieceList){
            if(isWhite == piece.isWhite && piece.name.equals("King")){
                return piece;
            }
        }
        return null;
    }

    public int getTileNum(int col, int row){
        return row * rows + col;
    }

    public void addPieces()
    {
        pieceList.add(new Rook(this,0, 0, false));
        pieceList.add(new Knight(this,1, 0, false));
        pieceList.add(new Bishop(this,2, 0, false));
        pieceList.add(new Queen(this,3, 0, false));
        pieceList.add(new King(this,4, 0, false));
        pieceList.add(new Bishop(this,5, 0, false));
        pieceList.add(new Knight(this,6, 0, false));
        pieceList.add(new Rook(this,7, 0, false));

        pieceList.add(new Pawn(this,0, 1, false));
        pieceList.add(new Pawn(this,1, 1, false));
        pieceList.add(new Pawn(this,2, 1, false));
        pieceList.add(new Pawn(this,3, 1, false));
        pieceList.add(new Pawn(this,4, 1, false));
        pieceList.add(new Pawn(this,5, 1, false));
        pieceList.add(new Pawn(this,6, 1, false));
        pieceList.add(new Pawn(this,7, 1, false));

        pieceList.add(new Rook(this,0, 7, true));
        pieceList.add(new Knight(this,1, 7, true));
        pieceList.add(new Bishop(this,2, 7, true));
        pieceList.add(new Queen(this,3, 7, true));
        pieceList.add(new King(this,4, 7, true));
        pieceList.add(new Bishop(this,5, 7, true));
        pieceList.add(new Knight(this,6, 7, true));
        pieceList.add(new Rook(this,7, 7, true));

        pieceList.add(new Pawn(this,0, 6, true));
        pieceList.add(new Pawn(this,1, 6, true));
        pieceList.add(new Pawn(this,2, 6, true));
        pieceList.add(new Pawn(this,3, 6, true));
        pieceList.add(new Pawn(this,4, 6, true));
        pieceList.add(new Pawn(this,5, 6, true));
        pieceList.add(new Pawn(this,6, 6, true));
        pieceList.add(new Pawn(this,7, 6, true));
    }

    private void updateGameStatus(){
        Piece king = findKing(isWhiteToMove);

        if(checkScanner.isGameOver(king)){
            if(checkScanner.isKingChecked(new Move(this, king, king.col, king.row))){
                System.out.println(isWhiteToMove ? "Black Wins!" : "White Wins!");
            }else{
                System.out.println("StaleMate");
            }
            isGameOver = true;
        } else if (insufficientMaterial(true) && insufficientMaterial(false)) {
            System.out.println("Insufficient Material!");
            isGameOver = true;
        }
    }

    private boolean insufficientMaterial(boolean isWhite){
        ArrayList<String> names = pieceList.stream()
                .filter(p -> p.isWhite == isWhite)
                .map(p -> p.name)
                .collect(Collectors.toCollection(ArrayList :: new));

        if(names.contains("Queen") || names.contains("Rook") || names.contains("Pawn")){
            return false;
        }
        return names.size() < 3;
    }

    public void paintComponent(Graphics g){
        Graphics2D g2D = (Graphics2D) g;

        //paint board
        for(int r = 0; r < rows; r++)
            for(int c = 0; c < cols; c++)
            {
                g2D.setColor((c + r) % 2 == 0 ? new Color(234, 209, 185) : new Color(145, 104, 56));
                g2D.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }

        // paint highlights
        if(selectedPiece != null)
            for(int r = 0; r < rows; r++)
                for(int c = 0; c < cols; c++){

                if(isValidMove(new Move(this,selectedPiece,c,r))){
                    g2D.setColor(new Color(55, 182, 74, 153));
                    g2D.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                }
            }

        // paint pieces
        for(Piece piece: pieceList)
        {
            piece.paint(g2D);
        }
    }
}
