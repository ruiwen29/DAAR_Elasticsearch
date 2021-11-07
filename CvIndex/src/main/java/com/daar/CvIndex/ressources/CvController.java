package com.daar.CvIndex.ressources;

import com.daar.CvIndex.models.CV;
import com.daar.CvIndex.services.CvService;
import com.daar.CvIndex.utils.ParsingPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/CV")
public class CvController {

    @Autowired
    private CvService cvService;

    @GetMapping(params = {"prenom"})
    public ResponseEntity<List<CV>> getCvByPrenom(@RequestParam("prenom") String prenom){
        return ResponseEntity.ok(cvService.findByPrenom(prenom));
    }

    @GetMapping(params = {"nom"})
    public ResponseEntity<List<CV>> getCvByNom(@RequestParam("nom") String nom){
        return ResponseEntity.ok(cvService.findByNom(nom));
    }

    @GetMapping(params = {"nom","prenom"})
    public ResponseEntity<List<CV>> getCvByNomAndPrenom(@RequestParam("nom") String nom,
                                                        @RequestParam("prenom") String prenom){
        return ResponseEntity.ok(cvService.findByNomAndPrenom(nom,prenom));
    }

    @GetMapping(params = {"filtre"})
    public ResponseEntity<List<CV>> getCvByMotsCles(@RequestParam("filtre") String filtre){
        return ResponseEntity.ok(cvService.findByMotsCles(filtre));
    }

    @GetMapping(params = {"filtres"})
    public ResponseEntity<List<CV>> getCvByMotsCles(@RequestParam("filtres") List<String> filtres){

        return ResponseEntity.ok(cvService.findByMotsCles(filtres));
    }

    @GetMapping(params = {"competence"})
    public ResponseEntity<List<CV>> getCvByCompetences(@RequestParam("competence") String filtre){
        return ResponseEntity.ok(cvService.findByCompetences(filtre));
    }

    @GetMapping(params = {"competences"})
    public ResponseEntity<List<CV>> getCvByCompetences(@RequestParam("competences") List<String> filtres){
        return ResponseEntity.ok(cvService.findByCompetences(filtres));
    }

    @GetMapping
    public ResponseEntity<Iterable<CV>> getCv() {
        return ResponseEntity.ok(cvService.findAll());
    }

    @PostMapping
    public ResponseEntity<CV> create(@RequestBody CV cv) {
        return ResponseEntity.created(URI.create("/CV")).body(cvService.save(cv));
    }


    @PostMapping("/file")
    public ResponseEntity<CV> envoiePDF(@RequestPart("file") MultipartFile file,
                                        @RequestPart("nom") String nom,
                                        @RequestPart("prenom") String prenom,
                                        @RequestPart("mail") String mail,
                                        @RequestPart("tel") String tel)
            throws IOException {

        File convFile = new File( "src/main/java/com/daar/CvIndex/utils/ressources/"+file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        CV cv;
        if(convFile.getPath().split("\\.")[1].equals("docx"))
            cv =ParsingPDF.parseDocx(nom,prenom,mail,tel,convFile);
        else
            cv =ParsingPDF.parse(nom,prenom,mail,tel,convFile);
        return ResponseEntity.created(URI.create("/CV")).body(cvService.save(cv));
    }




}
