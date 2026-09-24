import Table from 'react-bootstrap/Table'
import type { Meal, MealCategory } from '../../types'
import MealTableRow from './MealTableRow'

interface MealsTableProps {
	meals: Meal[]
	categories: MealCategory[]
	isAdmin: boolean
	onEdit: (meal: Meal) => void
	onDelete: (id: number) => void
}

const MealsTable = (props: MealsTableProps) => {
	const getCategoryName = (categoryId: number) =>
		props.categories.find((category) => category.id === categoryId)?.name ?? `#${categoryId}`

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
						{props.isAdmin && <th>Akcje</th>}
					</tr>
				</thead>
				<tbody>
					{props.meals.map((meal) => (
						<MealTableRow
							key={meal.id}
							meal={meal}
							categoryName={getCategoryName(meal.mealCategoryId)}
							isAdmin={props.isAdmin}
							onEdit={props.onEdit}
							onDelete={props.onDelete}
						/>
					))}
				</tbody>
			</Table>
			{!props.meals.length && <p>Brak posiłków.</p>}
		</>
	)
}

export default MealsTable
