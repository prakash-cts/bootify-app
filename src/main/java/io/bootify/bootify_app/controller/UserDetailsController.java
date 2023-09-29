package io.bootify.bootify_app.controller;

import io.bootify.bootify_app.domain.Department;
import io.bootify.bootify_app.model.UserDetailsDTO;
import io.bootify.bootify_app.repos.DepartmentRepository;
import io.bootify.bootify_app.service.UserDetailsService;
import io.bootify.bootify_app.util.CustomCollectors;
import io.bootify.bootify_app.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/userDetailss")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;
    private final DepartmentRepository departmentRepository;

    public UserDetailsController(final UserDetailsService userDetailsService,
            final DepartmentRepository departmentRepository) {
        this.userDetailsService = userDetailsService;
        this.departmentRepository = departmentRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("departmentValues", departmentRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Department::getId, Department::getDepartment)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("userDetailses", userDetailsService.findAll());
        return "userDetails/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("userDetails") final UserDetailsDTO userDetailsDTO) {
        return "userDetails/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("userDetails") @Valid final UserDetailsDTO userDetailsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "userDetails/add";
        }
        userDetailsService.create(userDetailsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("userDetails.create.success"));
        return "redirect:/userDetailss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("userDetails", userDetailsService.get(id));
        return "userDetails/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("userDetails") @Valid final UserDetailsDTO userDetailsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "userDetails/edit";
        }
        userDetailsService.update(id, userDetailsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("userDetails.update.success"));
        return "redirect:/userDetailss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        userDetailsService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("userDetails.delete.success"));
        return "redirect:/userDetailss";
    }

}
