package com.fernandocanabarro.oak_desafio.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fernandocanabarro.oak_desafio.dtos.ProductUpdateDTO;
import com.fernandocanabarro.oak_desafio.dtos.ProdutoRequestDTO;
import com.fernandocanabarro.oak_desafio.dtos.ProdutoResponseDTO;
import com.fernandocanabarro.oak_desafio.services.ProdutoService;
import com.fernandocanabarro.oak_desafio.services.csv.ProdutosCsvExporter;
import com.fernandocanabarro.oak_desafio.services.csv.ProdutosCsvImporter;
import com.fernandocanabarro.oak_desafio.services.excel.ProdutosExcelExporter;
import com.fernandocanabarro.oak_desafio.services.excel.ProdutosExcelImporter;
import com.fernandocanabarro.oak_desafio.services.pdf.ProdutosPdfExporter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ProdutosExcelImporter produtosExcelImporter;
    @Autowired
    private ProdutosCsvImporter produtosCsvImporter;

    @GetMapping("/")
    public String gridDeProdutos(Model model){
        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        model.addAttribute("produtos", produtos);
        return "produtos/grid";
    }

    @GetMapping("/admin/produtos")
    public String listagemDeProdutos(Model model){
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProdutoResponseDTO> page = produtoService.listarProdutosPaginados(pageable);

        int totalPageItems = page.getNumberOfElements();
        long totalItems = page.getTotalElements();
        int currentPage = 1;
        int totalPages = page.getTotalPages();

        model.addAttribute("totalPageItems", totalPageItems);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("produtos", page.getContent());
        model.addAttribute("sort", "id");
        model.addAttribute("sortDirection","asc");
        return "produtos/listagem";
    }

    @GetMapping("/admin/produtos/paginated")
    public String listagemDeProdutosPaginados(@RequestParam String page,
                                            @RequestParam String sort,
                                            @RequestParam String sortDirection,
                                            Model model){
        Pageable pageable = sortDirection.equals("asc") 
            ? PageRequest.of(Integer.parseInt(page) - 1, 10).withSort(Sort.by(sort).ascending())
            : PageRequest.of(Integer.parseInt(page) - 1, 10).withSort(Sort.by(sort).descending());

        Page<ProdutoResponseDTO> pageResponse = produtoService.listarProdutosPaginados(pageable);

        int totalPageItems = pageResponse.getNumberOfElements();
        long totalItems = pageResponse.getTotalElements();
        int currentPage = Integer.parseInt(page);
        int totalPages = pageResponse.getTotalPages();

        model.addAttribute("totalPageItems", totalPageItems);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("produtos", pageResponse.getContent());
        model.addAttribute("sort", sort);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        return "produtos/listagem";
    }

    // @GetMapping("/admin/produtos/order")
    // public String listagemDeProdutosPorValorAscendenteOuDecrescente(@RequestParam(name = "valor") String valor, Model model){
    //     List<ProdutoResponseDTO> produtos = valor.equals("asc") ? produtoService.listarPorValorAscendente()
    //         : produtoService.listarPorValorDecrescente();
    //     model.addAttribute("produtos", produtos);
    //     return "/produtos/listagem";
    // }

    @GetMapping("/admin/produtos/search")
    public String listarProdutosPorNome(@RequestParam(name = "nome") String nome, Model model){
        List<ProdutoResponseDTO> produtos = produtoService.listarPorNome(nome);
        model.addAttribute("produtos", produtos);
        return "produtos/listagem";
    }

    @GetMapping("/admin/produtos/cadastrar")
    public String formularioCadastroProduto(Model model){
        ProdutoRequestDTO produto = new ProdutoRequestDTO();
        model.addAttribute("produto", produto);
        return "produtos/cadastro-produto";
    }

    @PostMapping("/admin/produtos")
    public String cadastrarProduto(@Valid @ModelAttribute("produto") ProdutoRequestDTO produto, BindingResult bindingResult,Model model) throws IOException{
        if (bindingResult.hasErrors()) {
            model.addAttribute("produto", produto);
            return "produtos/cadastro-produto";
        }
        produtoService.adicionarProduto(produto);
        return "redirect:/admin/produtos";
    }

    @GetMapping("/admin/produtos/{id}/atualizar")
    public String formularioAtualizarProduto(@PathVariable Long id, Model model){
        ProductUpdateDTO produto = produtoService.encontrarProdutoParaUpdate(id);
        model.addAttribute("produto", produto);
        return "produtos/atualizar-produto";
    }

    @PostMapping("/admin/produtos/{id}")
    public String atualizarProduto(@PathVariable Long id, @Valid @ModelAttribute("produto") ProductUpdateDTO produto, BindingResult bindingResult,Model model) throws IOException{
        if (bindingResult.hasErrors()) {
            model.addAttribute("produto", produto);
            return "produtos/atualizar-produto";
        }
        produtoService.atualizarProduto(id,produto);
        return "redirect:/admin/produtos";
    }

    @GetMapping("/admin/produtos/{id}/deletar")
    public String deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
        return "redirect:/admin/produtos";
    }

    /* Exportar Excel */
    @GetMapping("/admin/produtos/excel/export")
    public void exportarParaExcel(HttpServletResponse response){
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm");
        String currentDateTime = LocalDateTime.now().format(dtf);
        String fileName = "produtos_" + currentDateTime + ".xlsx";

        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        ProdutosExcelExporter produtosExcelExporter = new ProdutosExcelExporter(produtos);
        produtosExcelExporter.export(response);
    }

    /* Exportar para Csv */
    @GetMapping("/admin/produtos/csv")
    public void exportarParaCsv(HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setCharacterEncoding("ISO-8859-1");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm");
        String currentDateTime = LocalDateTime.now().format(dtf);
        String fileName = "produtos_" + currentDateTime + ".csv";

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        ProdutosCsvExporter.export(response, produtos);
    }

    /* Exportar para PDF */
    @GetMapping("/admin/produtos/pdf")
    public void exportarParaPdf(HttpServletResponse response){
        response.setContentType("application/pdf");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm");
        String currentDateTime = LocalDateTime.now().format(dtf);
        String fileName = "produtos_" + currentDateTime + ".pdf";

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        ProdutosPdfExporter produtosPdfExporter = new ProdutosPdfExporter(produtos);
        produtosPdfExporter.export(response);
    }

    @PostMapping("/admin/produtos/excel/import")
    public String importarExcel(@RequestParam MultipartFile file,Model model){
        produtosExcelImporter.save(file);
        return "redirect:/admin/produtos";
    }

    @PostMapping("/admin/produtos/csv/import")
    public String importarCsv(@RequestParam MultipartFile file,Model model){
        produtosCsvImporter.save(file);
        return "redirect:/admin/produtos";
    }
}
