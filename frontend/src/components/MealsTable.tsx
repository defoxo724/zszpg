import type { Meal, MealCategory } from '../types'

interface MealsTableProps {
	meals: Meal[]
	categories: MealCategory[]
	isAdmin: boolean
	onEdit: (meal: Meal) => void
	onDelete: (id: number) => void
}

const MealsTable = ({ meals, categories, isAdmin, onEdit, onDelete }: MealsTableProps) => {
	const getCategoryName = (categoryId: number) => {
		const category = categories.find((item) => item.id === categoryId)
		return category?.name ?? `#${categoryId}`
	}

	return (
		<>
			<Table responsive striped>
				<thead>
					<tr>
						<th>Obraz</th>
						<th>Nazwa</th>
						<th>Opis</th>
						<th>Cena</th>
						<th>Kategoria</th>
						{isAdmin && <th>Akcje</th>}
					</tr>
				</thead>
				<tbody>
					{meals.map((meal) => (
						<tr key={meal.id}>
							<td>
								{meal.imageUrl ? (
									<img
										src={meal.imageUrl}
										alt={meal.name}
										width={80}
										height={60}
										className="rounded object-fit-cover"
									/>
								) : (
									<span className="text-secondary">Brak</span>
								)}
							</td>
							<td>{meal.name}</td>
							<td>{meal.description}</td>
							<td>{Number(meal.price).toFixed(2)} zł</td>
							<td>{getCategoryName(meal.mealCategoryId)}</td>
							{isAdmin && (
								<td className="text-nowrap">
									<Button
										className="me-2"
										size="sm"
										variant="outline-primary"
										onClick={() => onEdit(meal)}
									>
										Edytuj
									</Button>
									<Button
										size="sm"
										variant="outline-danger"
										onClick={() => onDelete(meal.id)}
									>
										Usuń
									</Button>
								</td>
							)}
						</tr>
					))}
				</tbody>
			</Table>
			{!meals.length && <p>Brak posiłków.</p>}
		</>
	)
}
export default MealsTable
import Button from 'react-bootstrap/Button'
import Table from 'react-bootstrap/Table'
