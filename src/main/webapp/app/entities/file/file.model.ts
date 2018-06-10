import { BaseEntity } from './../../shared';

export class File implements BaseEntity {
    constructor(
        public id?: number,
        public documentName?: string,
        public documentOneDetails?: string,
        public documentOneContentType?: string,
        public documentOne?: any,
        public documentTwoDetails?: string,
        public documentTwoContentType?: string,
        public documentTwo?: any,
        public documentThreeDetails?: string,
        public documentThreeContentType?: string,
        public documentThree?: any,
        public projectName?: string,
        public projectId?: number,
        public tasksId?: number,
    ) {
    }
}
