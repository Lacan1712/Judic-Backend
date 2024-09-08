package Utils.AlgorithmsUtil.Interfaces;

/**
 *
 * @param <T> é usado para o tipo de retorno gerado ao carregar a chave pública pelo algoritmo RSA
 * @param <I> é usado para o tipo de retorno gerado ao carregar a chave primária pelo algoritmo RSA
 */

public interface CryptographyRSAInterface<T,I>{
   T loadPublicKey();
   I loadPrivateKey();
}
