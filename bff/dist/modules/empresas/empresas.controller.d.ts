import { EmpresasService } from './empresas.service';
export declare class EmpresasController {
    private readonly service;
    constructor(service: EmpresasService);
    create(body: any): Promise<any>;
    findOne(id?: string, usuarioId?: string): Promise<any>;
    update(id: string, body: any): Promise<any>;
}
