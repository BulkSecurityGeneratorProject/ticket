import { BaseEntity } from './../../shared';

export const enum Status {
    'OPEN',
    'PROGRESS',
    'CLOSE'
}

export class Ticket implements BaseEntity {
    constructor(
        public id?: number,
        public ticket_name?: string,
        public ticket_desc?: string,
        public type?: string,
        public status?: Status,
    ) {
    }
}
