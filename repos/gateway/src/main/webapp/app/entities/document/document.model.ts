import { IDocumentType } from 'app/entities/document-type/document-type.model';
import { doc } from 'prettier';

export interface IDocument {
  id?: number;
  documentName?: string | null;
  employeeId?: number | null;
  type?: IDocumentType | null;
}

export class Document implements IDocument {
  constructor(
    public id?: number,
    public documentName?: string | null,
    public employeeId?: number | null,
    public type?: IDocumentType | null
  ) {}
}
export function getDocumentOwner(document: IDocument): number | undefined {
  return document.employeeId!;
}
export function getDocumentIdentifier(document: IDocument): number | undefined {
  return document.id;
}

