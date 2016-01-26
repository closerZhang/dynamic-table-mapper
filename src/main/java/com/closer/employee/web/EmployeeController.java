package com.closer.employee.web;

import com.closer.common.handler.TableProvider;
import com.closer.common.view.View;
import com.closer.company.domain.Company;
import com.closer.company.service.CompanyService;
import com.closer.employee.domain.Employee;
import com.closer.employee.service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工控制器
 * Created by closer on 2016/1/4.
 */
@RestController
@RequestMapping("/companies/{companyId}/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private CompanyService companyService;

    @JsonView(View.EagerDetail.class)
    @RequestMapping(value = "/{employeeId}",method = RequestMethod.GET)
    public Employee get(@PathVariable("companyId") long companyId,@PathVariable("employeeId") long employeeId) {
        Company company = companyService.findOne(companyId);
        TableProvider.setTablePrefix(company.getShortName());
        return service.findOne(employeeId);
    }

    @JsonView(View.List.class)
    @RequestMapping(method = RequestMethod.GET)
    public List list(@PathVariable long companyId) {
        Company company = companyService.findOne(companyId);
        TableProvider.setTablePrefix(company.getShortName());
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Employee add(@PathVariable long companyId, @RequestBody Employee employee) {
        Company company = companyService.findOne(companyId);
        TableProvider.setTablePrefix(company.getShortName());
        return service.add(employee);
    }
}
