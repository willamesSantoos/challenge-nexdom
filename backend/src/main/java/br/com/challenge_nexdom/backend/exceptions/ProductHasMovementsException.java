package br.com.challenge_nexdom.backend.exceptions;

public class ProductHasMovementsException extends RuntimeException {
    public ProductHasMovementsException(String code) {
        super("Não é possível excluir o produto de código " + code + ". Pois, existe movimentações de estoque!");
    }
}