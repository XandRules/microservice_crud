package com.xandrules.crud.services;

import com.xandrules.crud.data.vo.ProdutoVO;
import com.xandrules.crud.entity.Produto;
import com.xandrules.crud.exception.ResourceNotFoundException;
import com.xandrules.crud.repository.ProdutoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoVO create(ProdutoVO produto){

        return ProdutoVO.create(produtoRepository.save(Produto.create(produto)));
    }

    public Page<ProdutoVO> findAll(Pageable pageable){
        var page = produtoRepository.findAll(pageable);

        return page.map(this::convertToProdutoVO);
    }
    private ProdutoVO convertToProdutoVO(Produto produto){
        return ProdutoVO.create(produto);
    }

    public ProdutoVO findById(Long id){
        var entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado para esse id"));

        return ProdutoVO.create(entity);
    }

    public ProdutoVO update(@NotNull ProdutoVO produtoVO){
       findById(produtoVO.getId());

        return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));

    }

    public void delete( Long id){
        var entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum dado encontrado para esse id"));

         produtoRepository.delete(entity);

    }

}
