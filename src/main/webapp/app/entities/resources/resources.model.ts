import { BaseEntity } from './../../shared';

export class Resources implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public userLogin?: string,
        public userId?: number,
        public assigsId?: number,
        public tasksId?: number,
    ) {
    }
}
