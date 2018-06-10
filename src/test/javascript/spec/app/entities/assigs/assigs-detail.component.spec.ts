/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TicketTestModule } from '../../../test.module';
import { AssigsDetailComponent } from '../../../../../../main/webapp/app/entities/assigs/assigs-detail.component';
import { AssigsService } from '../../../../../../main/webapp/app/entities/assigs/assigs.service';
import { Assigs } from '../../../../../../main/webapp/app/entities/assigs/assigs.model';

describe('Component Tests', () => {

    describe('Assigs Management Detail Component', () => {
        let comp: AssigsDetailComponent;
        let fixture: ComponentFixture<AssigsDetailComponent>;
        let service: AssigsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TicketTestModule],
                declarations: [AssigsDetailComponent],
                providers: [
                    AssigsService
                ]
            })
            .overrideTemplate(AssigsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssigsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssigsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Assigs(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.assigs).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
