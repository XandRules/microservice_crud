package com.xandrules.crud.controller;

import com.xandrules.crud.data.vo.ProdutoVO;
import com.xandrules.crud.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final PagedResourcesAssembler<ProdutoVO> assembler;

    @Autowired
    public ProdutoController(ProdutoService produtoService, PagedResourcesAssembler<ProdutoVO> assembler) {
        this.produtoService = produtoService;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ProdutoVO findById(@PathVariable("id") Long id){
        return produtoService.findById(id)
                .add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
    }

    @GetMapping( produces = {"application/json"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "10") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction){

        var sortDirection = "desc".equalsIgnoreCase(direction)? Sort.Direction.DESC: Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page,limit,Sort.by(sortDirection, "nome") );
        Page<ProdutoVO> produtos = produtoService.findAll(pageable);
        produtos.stream()
                .forEach(p -> p.add(linkTo(methodOn(ProdutoController.class)
                        .findById(p.getId()))
                        .withSelfRel()));

        PagedModel<EntityModel<ProdutoVO>> pagedModel = assembler.toModel(produtos);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);

    }


    @PostMapping(produces = {"application/json"}, consumes = { "application/json"})
    public ProdutoVO create(@RequestBody ProdutoVO produtoVO){
        ProdutoVO p = produtoService.create(produtoVO);
                p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel());

        return p;
    }

    @PutMapping(produces = {"application/json"}, consumes = { "application/json"})
    public ProdutoVO update(@RequestBody ProdutoVO produtoVO){
        ProdutoVO p = produtoService.update(produtoVO);
        p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel());

        return p;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        produtoService.delete(id);

        return ResponseEntity.ok().build();
    }

}
