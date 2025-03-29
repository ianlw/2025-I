from Bio import SeqIO
from Bio.Seq import Seq
from Bio.SeqRecord import SeqRecord

fasta = "transcripcion_traduccion_02.fasta"
output_fasta = "secuencia_ARN.fasta"

arn_fasta = []

for _ in SeqIO.parse(fasta, "fasta"):
    print(f"\nID: {_.id}")
    adn = _.seq
    print(f"Secuencia ADN: {adn}")
    arn = adn.replace('T', 'U')
    print(f'Secuencia ARN: {arn}')
    arn_ = SeqRecord(Seq(arn), id=_.id, description="ARN transcrito")
    arn_fasta.append(arn_)

# Guardar en un archivo FASTA
with open(output_fasta, "w") as output_handle:
    SeqIO.write(arn_fasta, output_handle, "fasta")

print(f"Secuencias ARN guardadas en {output_fasta}")
