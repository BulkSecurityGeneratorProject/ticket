import { BaseEntity } from './../../shared';

export const enum IssueStatus {
    'NEW',
    'IN_PROGRESS',
    'PENDING',
    'RESOLVED',
    'CLOSED'
}

export class RaiseTicket implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public openDate?: any,
        public dueDate?: any,
        public issueStatus?: IssueStatus,
        public attachScreenshotContentType?: string,
        public attachScreenshot?: any,
        public remarks?: string,
        public projectName?: string,
        public projectId?: number,
        public assignedToLogin?: string,
        public assignedToId?: number,
    ) {
    }
}
