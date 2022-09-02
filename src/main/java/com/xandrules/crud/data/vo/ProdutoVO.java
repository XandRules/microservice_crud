package com.xandrules.crud.data.vo;

import com.xandrules.crud.entity.Produto;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProdutoVO extends RepresentationModel<ProdutoVO> implements Serializable {

    private Long id;

    private String nome;

    private Integer estoque;

    private Double preco;

    public static ProdutoVO create(Produto produto){
        return new ModelMapper().map(produto, ProdutoVO.class);
    }
}
