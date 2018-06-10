import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RaiseTicket } from './raise-ticket.model';
import { RaiseTicketPopupService } from './raise-ticket-popup.service';
import { RaiseTicketService } from './raise-ticket.service';

@Component({
    selector: 'jhi-raise-ticket-delete-dialog',
    templateUrl: './raise-ticket-delete-dialog.component.html'
})
export class RaiseTicketDeleteDialogComponent {

    raiseTicket: RaiseTicket;

    constructor(
        private raiseTicketService: RaiseTicketService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.raiseTicketService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'raiseTicketListModification',
                content: 'Deleted an raiseTicket'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-raise-ticket-delete-popup',
    template: ''
})
export class RaiseTicketDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private raiseTicketPopupService: RaiseTicketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.raiseTicketPopupService
                .open(RaiseTicketDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
