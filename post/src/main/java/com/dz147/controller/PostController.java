package com.dz147.controller;


/*import com.dz147.dao.AuthorMapper;
import com.dz147.dao.PostMapper;
import com.dz147.entity.Author;
import com.dz147.entity.Post;*/

import com.dz147.dao.AuthorMapper;
import com.dz147.dao.EmployeeMapper;
import com.dz147.dao.PostMapper;
import com.dz147.entity.Author;
import com.dz147.entity.EmpList;
import com.dz147.entity.Employee;
import com.dz147.entity.Post;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
public class PostController {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ExcelService excels = new ExcelService();

    //删除员工
    @RequestMapping(value = "/del/{number}")
    public String empDel(@PathVariable("number") String number) {
        employeeMapper.deleteByPrimaryKey(number);
        return "redirect:/ee";
    }


    @RequestMapping(value = "/ee")
    public String emp01(Model model) {
        List<Employee> employees = employeeMapper.selectAll();
        model.addAttribute("emps", employees);
        System.out.println("GG");
        return "empJson";
    }

    @RequestMapping(value = "/emp01", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String empAdd02(@Valid @RequestBody List<Employee> list, BindingResult result) {
        if (result.hasErrors()) {
            return "empJson";
        }
        for (Employee e : list) {
            employeeMapper.insert(e);
        }
        return "{\"msg\":\"succeed\"}";
    }

    @RequestMapping(value = "/emp02", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String empAdd03(@Valid @RequestBody Employee employees, BindingResult result) {
        if (result.hasErrors()) {
            return "empJson";
        }
        employeeMapper.insert(employees);
        return "{\"msg\":\"succeed\"}";
    }

    @RequestMapping(value = "/emp")
    public String empQuery(Model model) {
        List<Employee> employees = employeeMapper.selectAll();
        model.addAttribute("emps", employees);
        System.out.println("gg");
        return "empAdd";
    }

    @RequestMapping(value = "/empAdd", method = RequestMethod.POST)
    public String empAdd(Model model, @Valid EmpList empList, BindingResult result) {
        if (result.hasErrors()) {
            return "empAdd";
        }
        for (Employee emp : empList.addEmp) {
            employeeMapper.insert(emp);
        }
        return "redirect:/emp";
    }

    @RequestMapping(value = "/empAdd2", method = RequestMethod.POST)
    public String empAdd(Model model, @Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "empAdd";
        }
        employeeMapper.insert(employee);
        return "redirect:/emp";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(@RequestParam(defaultValue = "1") int page, Model model) {
        PageHelper.startPage(page, 1);
        List<Post> p = postMapper.selectAll();
        model.addAttribute("post", p);
        model.addAttribute("pageInfo", new PageInfo<>(p));
        List<Author> authors = authorMapper.selectAll();
        model.addAttribute("authors", authors);
        return "home";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(Post post) {
        postMapper.insert(post);
        return "redirect:/";
    }

    @RequestMapping(value = "/cc", method = RequestMethod.GET)
    public String gg(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("cc", "bb");
        return "author";
    }

    @RequestMapping(value = "/c/{cid}", method = RequestMethod.GET)
    public String cc(@PathVariable("cid") int id) {
        System.out.println(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> json() {
        PageHelper.startPage(1, 1);
        List<Post> p = postMapper.selectAll();
        return ResponseEntity.status(456).body(p);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAuthor(Author author) {
        authorMapper.insert(author);
        return "redirect:/";
    }

    /**
     *
     */
    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/u/{locale}")
    public String changeLocale(Model model, @PathVariable("locale") String localeStr, HttpServletRequest req, HttpServletResponse resp) {
        Locale locale = new Locale(localeStr);
        localeResolver.setLocale(req, resp, locale);
        List<Employee> employees = employeeMapper.selectAll();
        model.addAttribute("emps", employees);
        return "empAdd";
    }

    @RequestMapping(value = "/getFile", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String gg(MultipartFile file) {
        System.out.println(file.getOriginalFilename() + ";");
        return "{\"gg\":\"succeed\"}";
    }

    //图片上传
    @RequestMapping(value = "/addPicture", method = RequestMethod.POST, produces = "application/json;charset=utf-8;")
    public @ResponseBody
    String addPicture(MultipartFile file, Model model, HttpServletRequest request) throws IOException {
        //得到上传文件实际路径
        String realPath = request.getServletContext().getRealPath(File.separator + "imges");
        String s = excels.upLoadImg(file, realPath);
        return "{\"gg\":\"" + s + "\"}";
    }


    //Excel批量导入
    @RequestMapping(value = "/excelImport", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String excelImport(MultipartFile file) throws IOException {
        if (file.getSize() != 0) {
            InputStream is = file.getInputStream();
            Boolean aBoolean = excels.readExcelFromFileName(is);
            if (aBoolean == true) {
                return "{\"gg\":\"succeed\"}";
            }
        }
        return "{\"gg\":\"error\"}";
    }


    //下载方法
    @GetMapping("/download")
    ResponseEntity<InputStreamResource> fileDownload() throws IOException {
        FileSystemResource file = new FileSystemResource("F:" + File.separator + "图片素材" + File.separator + "个人文档" + File.separator + "9DUKEJI02.jpg");

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(file.contentLength());
        headers.setContentDispositionFormData("attachment", "liangwan.jpg");
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(file.getInputStream()));
    }

    @GetMapping("/excel")
    ResponseEntity<byte[]> excelDownload() {
        byte[] contents =
                new byte[0];
        try {
            contents = excels.exporTxcel(employeeMapper.selectAll());
            if(contents == null){
                return ResponseEntity.ok("err".getBytes());
            }
        } catch (IOException e) {
            return ResponseEntity.ok("err错误哦".getBytes());
        } catch (Exception e) {
            return ResponseEntity.ok("err".getBytes());
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        // new Date()为获取当前系统时间
        String now = df.format(new Date());
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(contents.length);
        headers.setContentDispositionFormData("attachment", "employeeInfo" + now + ".xls");

        return ResponseEntity.ok()
                .headers(headers)
                .body(contents);
    }

}
