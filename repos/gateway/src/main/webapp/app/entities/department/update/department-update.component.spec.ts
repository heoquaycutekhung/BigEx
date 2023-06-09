jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DepartmentService } from '../service/department.service';
import { IDepartment, Department } from '../department.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

import { DepartmentUpdateComponent } from './department-update.component';

describe('Component Tests', () => {
  describe('Department Management Update Component', () => {
    let comp: DepartmentUpdateComponent;
    let fixture: ComponentFixture<DepartmentUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let departmentService: DepartmentService;
    let employeeService: EmployeeService;
    let locationService: LocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DepartmentUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DepartmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepartmentUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      departmentService = TestBed.inject(DepartmentService);
      employeeService = TestBed.inject(EmployeeService);
      locationService = TestBed.inject(LocationService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Employee query and add missing value', () => {
        const department: IDepartment = { id: 456 };
        const manager: IEmployee = { id: 39820 };
        department.manager = manager;

        const employeeCollection: IEmployee[] = [{ id: 22996 }];
        jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
        const additionalEmployees = [manager];
        const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
        jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ department });
        comp.ngOnInit();

        expect(employeeService.query).toHaveBeenCalled();
        expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(employeeCollection, ...additionalEmployees);
        expect(comp.employeesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Location query and add missing value', () => {
        const department: IDepartment = { id: 456 };
        const location: ILocation = { id: 16862 };
        department.location = location;

        const locationCollection: ILocation[] = [{ id: 88431 }];
        jest.spyOn(locationService, 'query').mockReturnValue(of(new HttpResponse({ body: locationCollection })));
        const additionalLocations = [location];
        const expectedCollection: ILocation[] = [...additionalLocations, ...locationCollection];
        jest.spyOn(locationService, 'addLocationToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ department });
        comp.ngOnInit();

        expect(locationService.query).toHaveBeenCalled();
        expect(locationService.addLocationToCollectionIfMissing).toHaveBeenCalledWith(locationCollection, ...additionalLocations);
        expect(comp.locationsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const department: IDepartment = { id: 456 };
        const manager: IEmployee = { id: 2851 };
        department.manager = manager;
        const location: ILocation = { id: 56466 };
        department.location = location;

        activatedRoute.data = of({ department });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(department));
        expect(comp.employeesSharedCollection).toContain(manager);
        expect(comp.locationsSharedCollection).toContain(location);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Department>>();
        const department = { id: 123 };
        jest.spyOn(departmentService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ department });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: department }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(departmentService.update).toHaveBeenCalledWith(department);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Department>>();
        const department = new Department();
        jest.spyOn(departmentService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ department });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: department }));
        saveSubject.complete();

        // THEN
        expect(departmentService.create).toHaveBeenCalledWith(department);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Department>>();
        const department = { id: 123 };
        jest.spyOn(departmentService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ department });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(departmentService.update).toHaveBeenCalledWith(department);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackEmployeeById', () => {
        it('Should return tracked Employee primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEmployeeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackLocationById', () => {
        it('Should return tracked Location primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackLocationById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
