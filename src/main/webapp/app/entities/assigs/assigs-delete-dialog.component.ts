import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Assigs } from './assigs.model';
import { AssigsPopupService } from './assigs-popup.service';
import { AssigsService } from './assigs.service';

@Component({
    selector: 'jhi-assigs-delete-dialog',
    templateUrl: './assigs-delete-dialog.component.html'
})
export class AssigsDeleteDialogComponent {

    assigs: Assigs;

    constructor(
        private assigsService: AssigsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.assigsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'assigsListModification',
                content: 'Deleted an assigs'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-assigs-delete-popup',
    template: ''
})
export class AssigsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assigsPopupService: AssigsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.assigsPopupService
                .open(AssigsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
