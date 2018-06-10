/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TicketTestModule } from '../../../test.module';
import { ResourcesDetailComponent } from '../../../../../../main/webapp/app/entities/resources/resources-detail.component';
import { ResourcesService } from '../../../../../../main/webapp/app/entities/resources/resources.service';
import { Resources } from '../../../../../../main/webapp/app/entities/resources/resources.model';

describe('Component Tests', () => {

    describe('Resources Management Detail Component', () => {
        let comp: ResourcesDetailComponent;
        let fixture: ComponentFixture<ResourcesDetailComponent>;
        let service: ResourcesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TicketTestModule],
                declarations: [ResourcesDetailComponent],
                providers: [
                    ResourcesService
                ]
            })
            .overrideTemplate(ResourcesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResourcesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResourcesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Resources(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.resources).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
