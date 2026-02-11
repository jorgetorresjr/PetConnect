package exemplo.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/*
 * Todos os campos de Endereco serão armazenados na mesma tabela 
 * que armazena os dados de Usuario.
 */
@Embeddable
public class Endereco {

    @Column(name = "END_TXT_LOGRADOURO", length = 150, nullable = false)
    private String logradouro;
    
    @Column(name = "END_TXT_BAIRRO", length = 150, nullable = false)
    private String bairro;
    
    @Column(name = "END_NUMERO", length = 5, nullable = false)
    private Integer numero;
    
    @Column(name = "END_TXT_COMPLEMENTO", length = 30, nullable = true)
    private String complemento;
    
    @Pattern(regexp = "^\\d{2}\\.\\d{3}-\\d{3}$", message = "CEP inválido. Deve estar no formado NN.NNN-NNN, onde N é número natural")
    @NotNull
    @Column(name = "END_TXT_CEP", length = 20, nullable = false)
    private String cep;
    
    @Column(name = "END_TXT_CIDADE", length = 50, nullable = false)
    private String cidade;
    
    @Pattern(regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$", message = "Estado inválido")
    @NotNull
    @Column(name = "END_TXT_ESTADO", length = 50, nullable = false)
    private String estado;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
