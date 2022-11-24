package components.PawnPromotionComponent;

import components.ChessPieceComponent.ChessPiece;

public interface RenderPawnPromotionComponent {
    void render(PawnPromotionComponent pawnPromotionComponent, ChessPiece piece, ChessPiece pawnToUpdate);
}
