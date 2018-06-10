/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TicketTestModule } from '../../../test.module';
import { FileComponent } from '../../../../../../main/webapp/app/entities/file/file.component';
import { FileService } from '../../../../../../main/webapp/app/entities/file/file.service';
import { File } from '../../../../../../main/webapp/app/entities/file/file.model';

describe('Component Tests', () => {

    describe('File Management Component', () => {
        let comp: FileComponent;
        let fixture: ComponentFixture<FileComponent>;
        let service: FileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TicketTestModule],
                declarations: [FileComponent],
                providers: [
                    FileService
                ]
            })
            .overrideTemplate(FileComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FileComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new File(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.files[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
